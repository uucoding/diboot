import infinite from '@/components/common/infinite/infinite.vue';
import { Toast } from 'mint-ui';

export default {
  data() {
    return {
      query: {
        FUZZY_SEARCH: '',
      },
      queryObj: undefined,
      list: [],
      more: undefined,
    };
  },
  methods: {
    search() {
      this.list = [];
      this.queryObj = JSON.parse(JSON.stringify(this.query));
    },
    fail2load(res) {
      Toast({
        message: res.msg,
      });
    },
  },
  watch: {
    'query.FUZZY_SEARCH': function (FUZZY_SEARCH) {
      this.list = [];
      this.queryObj = { FUZZY_SEARCH };
    },
  },
  mounted() {
    this.$nextTick(async function () {
      if (this.attachMore) {
        const res = await this.$http.get(`/${this.name}/attachMore`, { type: 'view' });
        if (res.code === 0) {
          this.more = res.data;
        } else {
          Toast('获取元数据失败');
        }
      }
    });
  },
  components: {
    'my-infinite': infinite,
  },
  props: [
    'infiniteUrl',
  ],
};
