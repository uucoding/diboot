<template>
    <div class="error">
        <h2>Error</h2>
        <p>您要的页面走丢了，重试一下吧！</p>
    </div>
</template>

<script>
import { Indicator } from 'mint-ui';

export default {
  name: 'error',
  mounted() {
    Indicator.close();
  },
};
</script>

<style scoped>

</style>
