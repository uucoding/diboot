const TOKEN_API = '/token/apply';
// const WECHAT_TOKEN_API = '/applyToken/oauth/cp/MSG';
const WECHAT_TOKEN_API = process.env.NODE_ENV === 'development' ? '/applyToken/oauth/test' : '/applyToken/oauth/mp';
const GET_WEIXIN_JSSDK_API = '/wechat/mp/getJsSdkConfig';
const WEIXIN_JSSDK_API_LIST = ['uploadImage', 'chooseImage'];
// const GET_WEIXIN_JSSDK_API = '/wechat/cp/getJsSdkConfig/MSG';

export default {
  authType: 'login',
  AUTH_TYPE: {
    wechat: 'wechat',
    login: 'login',
  },
  authHeaderKey: 'authtoken',
  authStorageKey: 'token',
  authStorageTime: 'token_time',
  AUTH_REFRESH_INTERVAL: 1800000, // token刷新间隔 1800000
  requestTimeout: 10000,
  TOKEN_API,
  WECHAT_TOKEN_API,
  GET_WEIXIN_JSSDK_API,
  WEIXIN_JSSDK_API_LIST,
  noneAuthList: [],
};
