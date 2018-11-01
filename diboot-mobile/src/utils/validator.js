import Vue from 'vue';
import VeeValidate, { Validator } from 'vee-validate';
import zh_CN from 'vee-validate/dist/locale/zh_CN';
import VueI18n from 'vue-i18n';

export default {
  init() {
    Vue.use(VueI18n);
    const i18n = new VueI18n({
      locale: 'zh_CN',
    });

    Vue.use(VeeValidate, {
      i18n,
      i18nRootKey: 'validation',
      dictionary: {
        zh_CN,
      },
    });

    this.changeMessage();
    this.addRules();
  },
  changeMessage() {
    const dict = {
      // name为标签值（默认为name，可以重命名到data-vv-as属性）.
      zh_CN: {
        messages: {
          required: name => `${name} 不能为空`,
          length: (name, params) => `${name} 长度必须为${params[0]}位`,
          max: (name, params) => `${name} 长度最大为${params[0]}位`,
          min: (name, params) => `${name} 长度最小为${params[0]}位`,
        },
      },
    };
    Validator.localize(dict);
  },
  addRules() {
    Validator.extend('mobile', {
      getMessage: field => `${field} 请输入正确的手机号码`,
      validate: value => value.length === 11 && /^((13|14|15|17|18)[0-9]{1}\d{8})$/.test(value),
    });
    Validator.extend('Number', {
      getMessage: field => `${field} 请输入数字`,
      validate: value => /^-?\d+(\.\d+)?$/.test(value),
    });
    Validator.extend('phone', {
      getMessage: field => `${field} 请输入正确的手机号码`,
      validate: value => /^1\d{10}$/gi.test(value) || /^0\d{2,3}-?\d{7,8}$/.test(value),
    });
    Validator.extend('phonestr', {
      getMessage: field => `${field} 请输入正确的手机号码`,
      validate: value => /^(\+|\d)\d{10,12}/.test(value),
    });
    Validator.extend('password', {
      getMessage: field => `${field} 不符合规则要求，请输入6位及以上且须包含大小写字母及数字！`,
      validate: value => /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])[a-zA-Z\d]{6,32}$/.test(value),
    });
    Validator.extend('wechat', {
      getMessage: field => `${field} 不符合规则要求，请输入6-20个字母、数字、下划线或减号，以字母开头!`,
      validate: value => /^[a-zA-Z]{1}[-_a-zA-Z0-9]{5,19}$/.test(value),
    });
  },
};
