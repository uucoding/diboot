import Vue from 'vue';
import axios from './axios';
import wx from 'weixin-js-sdk';
import router from '../router';
import { Toast } from 'mint-ui';

import appConfig from '../config/index';
import validator from './validator';

export default {
  init() {
    // 挂载axios
    Vue.prototype.$http = axios;

    // 加载表单校验插件
    validator.init();

    // 定期刷新token
    setInterval(async () => {
      await this.applyToken();
    }, appConfig.AUTH_REFRESH_INTERVAL);
  },
  async applyToken() {
    // 先尝试使用微信方式及token方式换取token，如果不成功，则根据微信授权和登录授权来提示或者操作（微信授权的接口也可以用于登录方式的token交换）
    const code = this.getQueryString4hash('code');
    const state = this.getQueryString4hash('state');
    const res = await axios.post(appConfig.authType === appConfig.AUTH_TYPE.login ? appConfig.TOKEN_API : appConfig.WECHAT_TOKEN_API, { code, state });
    if (res.code === 0) {
      localStorage.setItem(appConfig.authStorageKey, res.data);
    } else if (appConfig.authType === appConfig.AUTH_TYPE.login) {
      // 如果是登陆方式，则跳转到登录页面
      router.push({ path: '/login' });
    } else {
      Toast(res.msg);
    }
    return res;
  },
  loadSdk() {
    // 如果为微信认证方式，则挂载js-sdk，并初始化weixin-js-sdk
    if (appConfig.authType === appConfig.AUTH_TYPE.wechat) {
      this.initWeixinJsSdk();
    }
  },
  initWeixinJsSdk() {
    /** *
     * 开启调试模式,调用的所有api的返回值会在客户端alert出来。
     * 若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
     */
    axios.post(appConfig.GET_WEIXIN_JSSDK_API, { url: window.location.href.split('#')[0] })
      .then((res) => {
        const data = res.data;
        wx.config({
          debug: false,
          appId: data.appId, // 必填，公众号的唯一标识
          timestamp: data.timestamp, // 必填，生成签名的时间戳
          nonceStr: data.nonceStr, // 必填，生成签名的随机串
          signature: data.signature, // 必填，签名，见附录1
          jsApiList: appConfig.WEIXIN_JSSDK_API_LIST,
        });
        wx.ready(() => {
          Vue.prototype.$weixin = true;
          Vue.prototype.$wx = wx;
        });
      });
  },
  getRedirectUrl(path) {
    return new Promise((resolve, reject) => {
      axios.get(appConfig.GET_WECHAT_REDIRECT_API, { path }).then((response) => {
        const data = response.data;
        if (data) {
          resolve(data.data);
        }
      }).catch((error) => {
        reject(error);
      });
    });
  },
  getQueryString4hash(name) {
    const href = window.location.href;
    const arr1 = href.split('?');
    if (arr1 && arr1.length > 1) {
      const paramstr = arr1[1];
      const params = paramstr.split('&');
      for (let i = 0; i < params.length; i++) {
        const paramObjs = params[i].split('=');
        if (paramObjs && paramObjs.length > 1 && paramObjs[0] === name) {
          return paramObjs[1];
        }
      }
    }

    return '';
  },
};
