import { Toast } from 'mint-ui';

const STATE_TYPE = 'warning';
export default {
  data() {
    return {
      ios: undefined,
      showValidateState: false,
      more: undefined,
      relationObj: {
        show: false,
        name: '',
        id: '',
        fieldName: '',
      },
      metadata: {
        showObj: {},
        actionListObj: {},
      },
    };
  },
  methods: {
    async submit() {
      this.showValidateState = true;
      if (!await this.validate()) {
        return false;
      }
      let res = {};
      if (this.model.pk === undefined) {
        // 新建
        res = await this.$http.post(`/${this.name}/`, this.model);
        this.handleResult(res, res.data ? res.data.id : '');
      } else {
        // 更新
        res = await this.$http.put(`/${this.name}/${this.model.pk}`, this.model);
        this.handleResult(res, this.model.pk);
      }
      return true;
    },
    async validate() {
      const result = await this.$validator.validateAll();
      const msg = this.getErrorMsg();
      msg ? Toast(msg) : '';
      return result;
    },
    getErrorMsg() {
      if (this.errors && this.errors.items && this.errors.items.length > 0) {
        const msgs = this.errors.items.map(item => item.msg);
        return msgs.join('、');
      }
      return '';
    },
    handleResult(res, id) {
      // 如果提交成功，则提示并跳转回详情页，若失败，则只提示失败信息
      Toast({
        message: res.msg,
      });
      if (res.code === 0) {
        this.$router.push({ path: `/${this.name}/detail/${id}` });
      }
    },
    merge2dist(src, dist) {
      Object.keys(dist).forEach((k) => {
        if (src[k] !== '' && src[k] !== undefined) {
          dist[k] = src[k];
        }
      });
    },
    // 打开关联面板选择关联项
    selectRelation(name, id, fieldName, targetValue, targetName) {
      this.relationObj.show = true;
      this.relationObj = {
        name, id, fieldName, targetValue, targetName,
      };
    },
    // 选择关联项之后赋值
    selectValue(obj) {
      if (obj && obj.item && obj.item[obj.id] && obj.item[obj.fieldName]) {
        this.model[obj.targetValue] = obj.item[obj.id];
        this.model[obj.targetName] = obj.item[obj.fieldName];
      }
      this.relationObj = {
        show: false,
        name: '',
        id: '',
        fieldName: '',
      };
    },
    // 显示元数据ActionSheet
    showMetadataAction(f) {
      this.showAction[f] = true;
    },
    // 将更多数据编辑到元数据的actionList选项列表中
    moredata2actionList(more) {
      if (!more || typeof more !== 'object') {
        return;
      }
      Object.keys(more).forEach((key) => {
        // 如果有k和v的值，则是元数据列表，编辑到actionList中
        if (more[key] && more[key].length > 0 && more[key][0].k && more[key][0].v) {
          const f = key.replace('Opts', '');
          this.metadata.showObj[f] = false;
          this.metadata.actionListObj[f] = more[key].map(item => ({
            name: item.k,
            method: () => {
              this.model[f] = item.v;
            },
          }));
        }
      });
    },
    // 打开相应字段的日期时间选择器
    openDateTimePicker(field) {
      this.$refs[`${field}Picker`].open();
    },
    // 判断是否是IOS操作系统
    isIOS() {
      const u = navigator.userAgent;
      return !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/);
    },
  },
  watch: {
  },
  computed: {
    validateStateObj() {
      if (this.showValidateState
          && this.errors && this.errors.items
          && this.errors.items.length > 0) {
        const fieldList = this.errors.items.map(item => item.field);
        if (fieldList && fieldList.length > 0) {
          const obj = {};
          fieldList.forEach((field) => {
            obj[field] = STATE_TYPE;
          });
          return obj;
        }
      }
      return {};
    },
  },
  mounted() {
    this.$nextTick(async function () {
      this.ios = this.isIOS();
      const id = this.$route.params.id;
      if (id !== undefined) {
        const res = await this.$http.get(`/${this.name}/${id}`);
        if (res.code === 0) {
          this.merge2dist(res.data, this.model);
        } else {
          Toast({
            message: res.msg,
          });
          this.$router.back();
        }
      }

      // 如果需要加载列表数据则加载列表数据
      if (this.attachMore) {
        const res = await this.$http.get(`/${this.name}/attachMore`);
        if (res.code === 0) {
          this.more = res.data;
          this.moredata2actionList(res.data);
        } else {
          Toast('加载选项列表出错');
        }
      }
    });
  },
};
