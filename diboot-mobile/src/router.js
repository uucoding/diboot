import Vue from 'vue';
import Router from 'vue-router';

import PageTransition from './components/common/PageTransition';
import Test from './views/test';
import Login from './views/login';
Vue.use(Router);

const mode = 'history';

const customRoutes = [];

const routes = [
  {
    path: '/',
    name: 'pageTransition',
    component: PageTransition,
    children: [{
      path: '/login',
      name: 'login',
      component: Login,
    },
    {
      path: '/test',
      name: 'test',
      component: Test,
    }].concat(customRoutes),
  },
];

export default new Router({ mode, routes });