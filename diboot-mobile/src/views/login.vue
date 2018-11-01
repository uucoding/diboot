<template>
    <div class="login">
        <form action="">
            <section class="box-logon">
                <div class="logon-list">
                    <i class="fa fa-user"></i>&nbsp;
                    <input type="text"
                           v-model="username"
                           name="username"
                           placeholder="请输入用户名"
                           id="username">
                </div>
                <div class="logon-list">
                    <i class="fa fa-paste"></i>&nbsp;
                    <input type="password"
                           v-model="password"
                           name="password"
                           placeholder="请输入密码"
                           id="password">
                </div>
            </section>
        </form>
        <p class="tipState">{{ tipText }}</p>
        <div class="logon-btn">
            <button type="button" class="prBtn" @click="login">立即登录</button>
        </div>
    </div>
</template>

<script>
import appConfig from '@/config/index';

export default {
  name: 'login',
  data() {
    return {
      username: '',
      password: '',
      tipState: false,
      tipText: '',
    };
  },
  mounted() {
    const timestamp = parseInt((new Date()).valueOf());
    if (localStorage.getItem('token') && localStorage.getItem('token_time') && parseInt(localStorage.getItem('token_time')) > timestamp) {
      this.$router.push({ path: '/' });
    }
  },
  methods: {
    toast(callback) {
      setTimeout(() => {
        callback();
      }, 1000);
    },
    async login() {
      const _self = this;
      if (!_self.username || !_self.password) {
        _self.tipState = true;
        _self.tipText = '请输入账号密码';
      } else {
        _self.tipState = false;
        const res = await this.$http.post(appConfig.TOKEN_API, {
          username: _self.username,
          password: _self.password,
        });
        if (res.code === 0) {
          _self.tipState = false;
          _self.tipText = '';
          localStorage.setItem(appConfig.authStorageKey, res.data);
          _self.toast(() => {
            _self.$router.push('/');
          });
        } else {
          _self.tipState = true;
          _self.tipText = '账号密码错误';
        }
      }
    },
  },
  components: {},
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
    .login {
        width: 100%;
        height: 100%;
        background-color: #f6f6f6;
    }

    .box-logon {
        background: #fff;
        margin-top: .5rem;
        padding: 0 .5rem;
    }

    .logon-list {
        height: 2.55rem;
        line-height: 2.55rem;
        border-bottom: 1px solid #f2f2f2;
    }

    .logon-list .iconfont {
        width: 8%;
        color: #999;
        float: left;
        font-size: 1rem;
    }

    .logon-list input {
        color: #999;
        font-size: .7rem;
        height: 2.5rem;
        line-height: normal;
        width: 90%;
        border-bottom: solid 1px #f2f2f2;
        outline: none;
    }

    .prBtn {
        width: 85%;
        margin: 1rem auto .5rem;
        height: 2rem;
        line-height: 2rem;
        background: #108de9;
        color: #fff;
        font-size: .8rem;
        text-align: center;
        display: block;
        border-radius: .25rem;
    }

    .tipState {
        color: red;
        padding: 10px 10% 0;
    }
</style>

