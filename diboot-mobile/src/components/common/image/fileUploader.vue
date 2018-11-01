<template>
    <div class="imgFileUploader">
        <img class="upload-img" v-for="(image, index) in imgUploader.previews"
             :src="image"
             :key="image + index"
             @click="removeImg(index)"
             alt="">
        <button type="button" v-show="imgUploader.links.length < imgUploader.maxImgCount"
                class="btn-wrapper btn btn-default btn-block btn-dashed">
            <input @change="addImages"
                   type="file"
                   accept="image/png,image/gif,image/jpg,image/jpeg"
                   class="imgUpload"
                   name="image_file">
            <i class="fa fa-plus"></i>上传图片（最多可上传{{ imgUploader.maxImgCount }}张）
        </button>
    </div>
</template>

<script>
import { Toast, MessageBox, Indicator } from 'mint-ui';

export default {
  name: 'imgFileUploader',
  data() {
    return {
      imgType: ['gif', 'jpeg', 'jpg', 'png'],
      imgUploader: {
        maxImgCount: 3,
        links: [],
        previews: [],
      },
    };
  },
  methods: {
    async addImages(e) {
      const fileName = e.target.value.toLowerCase();
      const suffixIndex = fileName.lastIndexOf('.');
      const suffix = fileName.substring(suffixIndex + 1).toLowerCase();
      let isImg = false;
      for (let i = 0; i < this.imgType.length; i++) {
        if (this.imgType[i] === suffix) {
          isImg = true;
          break;
        }
      }
      if (!isImg) {
        Toast({
          message: `选择文件错误,图片类型必须是${this.imgType.join('，')}中的一种`,
        });
        return false;
      }
      const formData = new FormData();
      const file = e.target.files[0];
      formData.append('image', file);
      Indicator.open({
        text: '上传中...',
        spinnerType: 'fading-circle',
      });
      const res = await this.$http.upload(this.uploadUrl, formData);
      Indicator.close();
      if (res.code === 0) {
        this.imgUploader.links.push(res.data);
        this.imgUploader.previews.push(this.getObjectUrl(file));
      }
      Toast({
        message: res.msg,
      });
      return true;
    },
    getObjectUrl(file) {
      let url = null;
      if (window.createObjectURL !== undefined) {
        url = window.createObjectURL(file);
      } else if (window.URL !== undefined) {
        url = window.URL.createObjectURL(file);
      } else if (window.webkitURL !== undefined) {
        url = window.webkitURL.createObjectURL(file);
      }
      return url;
    },
    removeImg(index) {
      if (this.imgUploader.links && this.imgUploader.links.length > 0) {
        MessageBox.confirm('确定删除此照片？').then(() => {
          this.imgUploader.links.splice(index, 1);
          this.imgUploader.previews.splice(index, 1);
        });
      }
    },
  },
  mounted() {
    this.$nextTick(function () {
      if (this.maxCount) {
        this.imgUploader.maxImgCount = this.maxCount;
      }
    });
  },
  watch: {
    'imgUploader.links': function (arr) {
      this.$emit('input', arr.join(','));
    },
    value(val) {
      if (val) {
        this.imgUploader.links = val.split(',');
        this.imgUploader.previews = val.split(',');
      }
    },
  },
  computed: {},
  props: [
    'maxCount',
    'uploadUrl',
    'value',
  ],
};
</script>

<style scoped>
    .imgFileUploader{
        width: 100%;
    }
    .imgFileUploader button{
        text-align: center;
    }
    .wxUploader {
        height: auto;
    }
    .picture {
        position: relative;
        width: 100px;
        height: 100px;
        border-radius: 8px;
        margin-top: 20px;
        background-repeat: no-repeat;
        -webkit-background-size: contain;
        background-size: contain;
        background-position: center center;
    }

    .add-picture-btn {
        position: relative;
        width: 100px;
        height: 100px;
        border-radius: 8px;
        margin-top: 20px;
        background-color: #d7d7d7;
    }

    .add-picture-btn > i {
        position: absolute;
        width: 60px;
        height: 60px;
        top: 50%;
        left: 50%;
        margin-top: -30px;
        margin-left: -30px;
        text-align: center;
        line-height: 60px;
        text-decoration: none;
        color: #999;
        font-size: 50px;
    }

    .item {
        width: 32%;
        float: left;
    }

    .clear {
        clear: both;
    }

    /* 长按钮上传按钮样式 */
    .btn {
        display: inline-block;
        padding: 6px 12px;
        margin-bottom: 0;
        font-size: 14px;
        font-weight: 400;
        line-height: 1.42857143;
        text-align: center;
        white-space: nowrap;
        vertical-align: middle;
        -ms-touch-action: manipulation;
        touch-action: manipulation;
        cursor: pointer;
        -webkit-user-select: none;
        -moz-user-select: none;
        -ms-user-select: none;
        user-select: none;
        background-image: none;
        border: 1px solid transparent;
        border-radius: 4px;
        background-color: #fff;
        outline: none;
    }

    .btn-block {
        display: block;
        width: 100%;
    }

    .btn-dashed {
        border: 1px dashed #cecece;
        -webkit-border-radius: 0;
        border-radius: 0;
        text-align: left;
        color: #989898;
    }

    .upload-img {
        display: block;
        height: auto;
        width: 100%;
        margin-top: 1px;
    }

    .btn-wrapper {
        position: relative;
    }

    .btn-wrapper .imgUpload {
        position: absolute;
        display: block;
        height: 100%;
        width: 100%;
        top: 0;
        left: 0;
        opacity: 0;
        outline: none;
    }
</style>
