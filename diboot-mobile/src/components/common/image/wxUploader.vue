<template>
    <div class="wxUploader">
        <img class="upload-img"
             v-for="(image, index) in previewIds"
             :src="image"
             :key="image + index"
             @click="removeImg(index)"
             alt="">
        <button type="button"
                v-show="imgUploader.localIds.length < imgUploader.maxImgCount"
                @click="addImages"
                class="btn btn-default btn-block btn-dashed">
            <i class="fa fa-plus"></i>
            上传图片（最多可上传{{ imgUploader.maxImgCount }}张）
        </button>
    </div>
</template>

<script>
import { Toast, MessageBox } from 'mint-ui';

export default {
  name: 'wxUploader',
  data() {
    return {
      imgUploader: {
        maxImgCount: 3,
        localIds: [],
        serverIds: [],
      },
    };
  },
  methods: {
    addImages() {
      if (!this.$weixin) {
        MessageBox.alert('还没有准备好，稍等下吧！', '提示');
        return false;
      }
      this.chooseImages();
      return true;
    },
    /**
             * 从手机或相机选择照片
             */
    chooseImages() {
      const _this = this;
      let sourceType = ['album', 'camera'];
      if (this.sourceType) {
        sourceType = this.sourceType;
      }
      let sizeType = ['original'];
      if (this.sizeType) {
        sizeType = this.sizeType;
      }

      this.$wx.chooseImage({
        count: this.imgUploader.maxImgCount - this.imgUploader.localIds.length,
        sizeType,
        sourceType,
        success(res) {
          // alert("选择图片成功")
          const localIds = res.localIds;
          for (let i = 0; i < localIds.length; i++) {
            _this.imgUploader.localIds.push(localIds[i]);
          }
          _this.uploadImageList(localIds, 0);
        },
        fail(res) {
          MessageBox.alert(`选择图片失败，${JSON.stringify(res)}`, '提示');
        },
      });
    },
    /**
             * 顺序上传图片递归调用
             * @param localIds 图片数组
             * @param idx    当前序号
             * @param len    数组长度
             */
    async uploadImageList(localIds, idx) {
      const len = localIds.length;

      const serverId = await this.uploadImage(localIds[idx]);
      // 记录serverId
      this.imgUploader.serverIds.push(serverId);
      if (idx + 1 < len) {
        await this.uploadImageList(localIds, idx + 1);
      } else {
        Toast({
          message: '上传成功',
        });
      }
    },
    uploadImage(localId) {
      return new Promise((resolve) => {
        this.$wx.uploadImage({
          localId, // 需要上传的图片的本地ID，由chooseImage接口获得
          isShowProgressTips: 1, // 默认为1，显示进度提示
          success(res) {
            const serverId = res.serverId; // 返回图片的服务器端ID
            resolve(serverId);
          },
        });
      });
    },
    removeImg(index) {
      if (this.imgUploader.localIds && this.imgUploader.localIds.length > 0) {
        MessageBox.confirm('确定删除此照片？').then(() => {
          this.imgUploader.localIds.splice(index, 1);
          this.imgUploader.serverIds.splice(index, 1);
        });
      }
    },
    getLocalImageData(localId) {
      // alert("开始IOS预览图片")
      return new Promise((resolve) => {
        this.$wx.getLocalImgData({
          localId,
          success(res) {
            // alert("IOS预览图片成功")
            resolve(res.localData);
          },
        });
      });
    },
    isIOS() {
      // 如果需要对IOS做单独的预览判断，则使用改段代码，否则始终返回false即可
      const u = navigator.userAgent;
      return !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/);
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
    'imgUploader.serverIds': function (arr) {
      const images = arr.join(',');
      this.$emit('input', images);
    },
    value(val) {
      if (val) {
        this.imgUploader.localIds = val.split(',');
        this.imgUploader.serverIds = val.split(',');
      }
    },
  },
  computed: {
    previewIds() {
      // let previewIds = []
      const localIds = this.imgUploader.localIds;
      return localIds;
      // if (!this.isIOS()){
      //   return localIds
      // }
      // if (localIds && localIds.length > 0){
      //   for (let i=0; i<localIds.length; i++){
      //       let previewId = await _this.getLocalImageData(localIds[i])
      //       previewIds.push(previewId)
      //   }
      // }
      // return previewIds
    },
  },
  props: [
    'maxCount',
    'sourceType',
    'sizeType',
    'value',
  ],
};
</script>

<style scoped>
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
</style>
