import Vue from 'vue';
import App from './App.vue';
import router from './router';
import store from './store';

// 引入Mint-UI
import Mint from 'mint-ui';
import 'mint-ui/lib/style.css';
import * as filters from './filter/index';
import startUtils from './utils/start';

Vue.config.productionTip = false;
Vue.use(Mint);

// 添加所有过滤器
Object.keys(filters).forEach((key) => {
  Vue.filter(key, filters[key]);
});

// 启动初始化
startUtils.init();
startUtils.applyToken().then(() => {
  new Vue({
    router,
    store,
    render: h => h(App),
  }).$mount('#app');

  startUtils.loadSdk();
});

