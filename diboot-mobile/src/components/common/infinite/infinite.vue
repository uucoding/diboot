<template>
    <div class="commonInfinate" >
        <div v-infinite-scroll="loadMore"
                infinite-scroll-disabled="loading"
                infinite-scroll-distance="40"
                infinite-scroll-immediate-check="state">
            <slot name="infinite"></slot>
            <div class="page-infinite-loading" v-if="loadingtip">
                <div style="margin: auto;width:28px;padding-bottom: 1rem" v-show="tip">
                    <mt-spinner type="fading-circle" ></mt-spinner>
                </div>
                <slot name="loading" v-if="text"></slot>
                <slot name="ending" v-else-if="!loadState"></slot>
            </div>
            <div class="page-infinite-loading"
                 v-if="!loadState && currentPage==1 && infiniteList.length==0">
                <slot name="nodata"></slot>
            </div>
        </div>
    </div>
</template>
<script>
export default {
  data() {
    return {
      loading: false,
      loadState: false, // 是否在加载中
      loadingtip: false, // 是否显示加载成功
      text: true, // 文字提示
      tip: true, // 加载图表提示是否显示
      currentPage: 1, // 当前页
      state: false,
    };
  },
  methods: {
    timeout() {
      return new Promise(((resolve) => {
        setTimeout(() => {
          resolve();
        }, 1000);
      }));
    },
    async loadMore() {
      const _this = this;
      _this.loading = true;
      let resultdata = {};

      // build查询条件
      let queryObj = {};
      if (this.query && Object.keys(this.query).length > 0) {
        queryObj = JSON.parse(JSON.stringify(this.query));
      }
      queryObj.currentPage = this.currentPage;
      queryObj.pageSize = this.pageSize;
      // ajax请求加载数据
      this.loadState = true;
      if (_this.ajaxType.toLocaleUpperCase() === 'POST') {
        resultdata = await this.$http.post(_this.infiniteUrl, queryObj);
      } else {
        resultdata = await this.$http.get(_this.infiniteUrl, queryObj);
      }

      // 通知父组件 加载完成回调
      this.$emit('loadComplete', resultdata.data);

      this.loadState = false;
      // 如果请求失败就返回
      if (resultdata.code !== 0) {
        _this.loadingtip = false;
        _this.loading = true;
        _this.$emit('fail2load', resultdata);
      } else {
        _this.loadingtip = true;
        _this.infiniteList.push(...resultdata.data);
        // 如果得到的加载数据数量小于设置的数量
        if (resultdata.data.length < _this.pageSize) {
          _this.text = false;
          _this.tip = false;
          _this.loading = true;
        } else {
          // 每次加载完一页后就把当前页数加1
          _this.currentPage += 1;
          _this.loadingtip = false;
          _this.loading = false;
        }
      }
    },
  },
  watch: {
    query: {
      handler() {
        this.currentPage = 1;
        this.loadMore();
      },
      deep: true,
    },
  },
  // 钩子函数
  mounted() {
    this.loadMore();
  },
  // infinitelist:起始数据,infiniteUrl:数据加载请求的url,ajaxType:数据请求方式,pageSize:一次加载的数据量
  props: ['infiniteList', 'infiniteUrl', 'ajaxType', 'pageSize', 'query'],
};
</script>

<style scoped>
</style>
