<template>
    <div class="imageView">
        <mt-popup
                v-model="imageUrlPanel" class="mint-popup-fullscreen"
                position="right">
            <mt-header fixed title="图片">
                <mt-button slot="left" icon="back" @click="closePanel()"></mt-button>
            </mt-header>
            <div style="height: 100%; overflow-y: auto;">
                <img :src="imageUrl" style="width: 100%; margin-top: 40px;">
            </div>
        </mt-popup>
    </div>
</template>

<script>
export default {
  name: 'imageVue',
  data() {
    return {
      imageUrlPanel: true,
    };
  },
  methods: {
    closePanel(val) {
      this.imageUrlPanel = false;
      if (!val) {
        this.$emit('closePanel');
      }
    },
  },
  props: ['imageUrl'],
};
</script>

<style scoped>

</style>
