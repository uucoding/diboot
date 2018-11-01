<template>
    <div id="app">
        <router-view></router-view>
    </div>
</template>
<script>
import appConfig from './config/index';

export default {
  name: 'app',
  data() {
    return {
      currentRoute: this.$route.path,
    };
  },
  methods: {
    async initToken() {
      if (this.currentRoute.indexOf('login') !== -1) {
        return false;
      }
      const token = localStorage.getItem(appConfig.authStorageKey);

      let valid = false;
      if (token) {
        // 如果有token，则校验该token是否有效
        const res = await this.$http.post('/refreshToken');
        if (res.code === 0) {
          valid = true;
          localStorage.setItem(appConfig.authStorageKey, res.data);
        }
      }

      if (valid) {
        return false;
      }

      if (appConfig.authType === appConfig.AUTH_TYPE.login) {
        this.$router.push({ path: '/login' });
      } else if (appConfig.authType === appConfig.AUTH_TYPE.wechat) {
        // 否则获取重定向路径，然后跳转到重定向链接中
        // 获取router中的路径
        const redirectUrl = await this.getRedirectUrl(this.currentRoute);
        window.location.href = redirectUrl;
      }
      return true;
    },
    getRedirectUrl(path) {
      return new Promise((resolve, reject) => {
        this.$http.get(appConfig.GET_WECHAT_REDIRECT_API, { path }).then((response) => {
          const { data } = response.data;
          if (data) {
            resolve(data);
          }
        }).catch((error) => {
          reject(error);
        });
      });
    },
  },
  async mounted() {
    // console.log('init....')
    // await this.initToken()
  },
};
</script>
<style lang="stylus" rel="stylesheet/stylus">
    @import 'https://cdn.bootcss.com/animate.css/3.5.2/animate.min.css'
    @import 'https://cdn.bootcss.com/font-awesome/4.7.0/css/font-awesome.min.css';
    @import "common/stylus/custom/mint-ui.styl"
    #app
        font-family 'Avenir', Helvetica, Arial, sans-serif
        -webkit-font-smoothing antialiased
        -moz-osx-font-smoothing grayscale
        text-align center
        color #2c3e50

    #nav
        padding 30px
        a
            font-weight bold
            color #2c3e50
            &.router-link-exact-active
                color #42b983
</style>
