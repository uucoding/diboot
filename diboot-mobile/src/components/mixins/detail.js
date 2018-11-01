import { Toast, MessageBox } from 'mint-ui';

export default {
  data() {
    return {
      model: {},
      roles: '',
      currentUserId: undefined,
      showBox: false,
      more: undefined,
      imageUrl: '',
    };
  },
  methods: {
    edit() {
      this.$router.push({ path: `/${this.name}/update/${this.model.pk}` });
    },
    showImage(imgurl) {
      this.imageUrl = imgurl;
    },
    closeImgPanel() {
      this.imageUrl = '';
    },
    remove() {
      MessageBox.confirm('确定删除该条数据？').then(async () => {
        const id = this.$route.params.id;
        const res = await this.$http.delete(`/${this.name}/${id}`);
        Toast({
          message: res.msg,
        });
        if (res.code === 0) {
          this.$router.push({ path: `/${this.name}/list` });
        }
      });
    },
    async afterLoadDetail() {
      return true;
    },
  },
  computed: {
    rightWidth() {
      return window.innerWidth * 0.7;
    },
  },
  async mounted() {
    if (!this.$route.params.id) {
      Toast({
        message: '未获取到该条记录',
        iconClass: 'icon icon-danger',
      });
      this.$router.back();
    }
    const id = this.$route.params.id;
    let res = await this.$http.get(`/${this.name}/${id}`);
    if (res.code === 0) {
      this.model = res.data;
      this.showBox = true;
      await this.afterLoadDetail();
    } else {
      Toast({
        message: res.msg || '获取数据失败',
        iconClass: 'icon icon-danger',
      });
    }

    // 如果需要加载其他数据，则开始加载其他数据
    if (this.attachMore) {
      res = await this.$http.get(`/${this.name}/attachMore`, { type: 'view' });
      if (res.code === 0) {
        this.more = res.data;
      } else {
        Toast('获取元数据失败');
      }
    }
  },
};
