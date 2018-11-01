<!-- 这个vue文件是所有路由的父节点 路由跳转的token验证  路由的动画效果都是写在这-->

<template>
    <div id='PageTransition'>
        <!-- 路由的过渡效果 -->
        <transition :name="transitionName">
            <router-view class="child-view"></router-view>
        </transition>
    </div>
</template>
<script>
// 引入hanmerjs(用于手动左右切换)如不需要就注释
import Hammer from 'hammerjs';

export default {
  data() {
    return {
      transitionName: 'slide-left',
    };
  },
  methods: {
    goback() {
      this.$router.back(-1);
    },
    isIOS() {
      // 如果需要对IOS做单独的预览判断，则使用改段代码，否则始终返回false即可
      const u = navigator.userAgent;
      return !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/);
    },
  },
  watch: {
    $route(to, from) {
      const toDepth = to.path.split('/').length;
      const fromDepth = from.path.split('/').length;
      this.transitionName = toDepth < fromDepth ? 'slide-right' : 'slide-left';
    },
  },
  // 钩子函数
  mounted() {
    const _self = this;
    // hanmer插件
    const start = document.getElementById('app');
    const hammerstart = new Hammer(start);
    // 插件手动左右切换
    if (!this.isIOS()) {
      hammerstart.on('swiperight', () => {
        _self.goback();
      });
    }
  },
};
</script>

<style scoped lang="stylus" rel="stylesheet/stylus">
    .child-view
        position: absolute
        width:100%
        transition: all .8s cubic-bezier(.55,0,.1,1)
    /*左滑右滑的样式*/
    .slide-left-enter, .slide-right-leave-active
        opacity: 0
        -webkit-transform: translate(100px, 0)
        transform: translate(100px, 0)
    .slide-left-leave-active, .slide-right-enter
        opacity: 0
        -webkit-transform: translate(-100px, 0)
        transform: translate(-100px, 0)
    .header
        height:44px
        background:#0058f1
        width:100%
    #PageTransition
        width: 100%
        height: 100%
</style>
