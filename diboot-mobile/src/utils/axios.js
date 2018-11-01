// 引入axios
import axios from 'axios';
import qs from 'qs';
import { Indicator } from 'mint-ui';

import appConfig from '../config/index';

// 定义cancel所需要的参数
const CancelToken = axios.CancelToken;
let lastGetUrl = '';

// 接口前缀（本地接口地址代理中也可以将前缀写成跟生产接口前缀一样，便于开发测试）
axios.defaults.baseURL = '/rest';

// 请求的拦截器
axios.interceptors.request.use(async (config) => {
  /** *
     * 一般来说要把这些token信息保存在localstorage或者用vuex存储
     ** */

  // 如果当前请求在忽略认证的列表中，不进行拦截校验
  const noneAuthList = appConfig.noneAuthList;
  const currentUrl = typeof config.url === 'string' ? config.url : config.url.url;
  if (noneAuthList && noneAuthList.length > 0) {
    for (let i = 0; i < noneAuthList.length; i++) {
      const noneAuthUrl = noneAuthList[i];
      if (currentUrl.indexOf(noneAuthUrl) !== -1) {
        return config;
      }
    }
  }

  // 如果当前页是login页，不进行拦截校验
  if (window.location.href.indexOf('login') !== -1) {
    return config;
  }

  // 校验当前token是否超时
  const token = localStorage.getItem(appConfig.authStorageKey);

  // 判断token是否存在及有效，如果存在并有效，则将token设置到header中，并返回config
  if (token) {
    if (!config.headers[appConfig.authHeaderKey]) {
      config.headers[appConfig.authHeaderKey] = token;
    }
    return config;
  }

  return config;
}, (error) => {
  Indicator.close();
  return Promise.reject(error);
});

axios.interceptors.response.use((response) => {
  Indicator.close();
  return response;
}, (error) => {
  Indicator.close();
  if (error.response) {
    if (error.response.status === 200
        || error.response.status === 304
        || error.response.status === 400) {
      return Promise.reject(error.response.data);
    }
    // router.push({
    //     path: '/error'
    // })

    return Promise.reject(error.response.data);
  }
  return Promise.reject('请求出错');
});

function checkStatus(response) {
  // loading
  // 如果http状态码正常，则直接返回数据
  if (response && (response.status === 200 || response.status === 304 || response.status === 400)) {
    // 如果不需要除了data之外的数据，可以直接 return response.data
    return response.data;
  }
  // 异常状态下，把错误信息返回去
  return {
    status: -404,
    msg: '网络异常',
  };
}

function checkCode(res) {
  // 如果code异常(这里已经包括网络错误，服务器错误，后端抛出的错误)，可以弹出一个错误提示，告诉用户
  return res;
}

let cancel;

export default {
  // 请求的时候一般传三个参数：请求地址,传递的值(对象),header头部(token,可不传)
  get(url, params, header) {
    Indicator.open({
      text: '加载中...',
      spinnerType: 'fading-circle',
    });

    cancel && lastGetUrl === url && typeof cancel === 'function' && cancel();
    return axios.get(url, {
      params, // get 请求时带的参数
      cancelToken: new CancelToken(((c) => {
        // An executor function receives a cancel function as a parameter
        cancel = c;
        lastGetUrl = url;
      })),
      timeout: appConfig.requestTimeout,
      headers: {
        'X-Requested-With': 'XMLHttpRequest',
        'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8',
        authtoken: header,
      },
    }).then(response => checkStatus(response)).then(res => checkCode(res)).catch((thrown) => {
      if (axios.isCancel(thrown)) {
        console.log('Request canceled', thrown.message);
      } else {
        console.log('GET request exception', thrown.message);
      }
    });
  },
  post(url, data, header) {
    Indicator.open({
      spinnerType: 'fading-circle',
    });
    return axios({
      method: 'POST',
      url,
      data: qs.stringify(data),
      timeout: appConfig.requestTimeout,
      headers: {
        'X-Requested-With': 'XMLHttpRequest',
        'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8',
        authtoken: header,
      },
    }).then(response => checkStatus(response)).then(res => checkCode(res)).catch((thrown) => {
      console.log('post exception', thrown.message);
    });
  },
  put(url, data, header) {
    Indicator.open({
      text: '提交中...',
      spinnerType: 'fading-circle',
    });
    return axios({
      method: 'PUT',
      url,
      data: qs.stringify(data),
      timeout: appConfig.requestTimeout,
      headers: {
        'X-Requested-With': 'XMLHttpRequest',
        'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8',
        authtoken: header,
      },
    }).then(response => checkStatus(response)).then(res => checkCode(res)).catch((thrown) => {
      console.log('put exception', thrown.message);
    });
  },
  delete(url, params, header) {
    Indicator.open({
      text: '删除中...',
      spinnerType: 'fading-circle',
    });
    return axios.delete(url, {
      params, // get 请求时带的参数
      timeout: appConfig.requestTimeout,
      headers: {
        'X-Requested-With': 'XMLHttpRequest',
        'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8',
        authtoken: header,
      },
    }).then(response => checkStatus(response)).then(res => checkCode(res)).catch((thrown) => {
      console.log('delete exception', thrown.message);
    });
  },
  upload(url, data, header) {
    return axios({
      method: 'POST',
      url,
      data,
      timeout: 30000,
      headers: {
        'Content-Type': 'multipart/form-data',
        authtoken: header,
      },
    }).then(response => checkStatus(response)).then(res => checkCode(res)).catch((thrown) => {
      console.log('upload exception', thrown.message);
    });
  },
};
