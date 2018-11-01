<template>
    <mt-popup v-model="showPanel" class="mint-popup-fullscreen" position="right">
        <mt-header fixed title="选择">
            <mt-button slot="left" icon="back" @click="toggleSelectPanel"></mt-button>
        </mt-header>
        <div style="margin-top: 40px; overflow-y: auto;">
            <my-infinite @fail2load="fail2load"
                         :infiniteList='list'
                         :infiniteUrl="loadUrl"
                         ajaxType="GET"
                         pageSize="20"
                         :query="queryObj">
                <div slot="infinite">
                    <div class="search-box">
                        <mt-search v-model="query.FUZZY_SEARCH"></mt-search>
                    </div>
                    <div @click="selectValue(item)" v-for="item in list" :key="item.id">
                        <mt-cell :title="item[fieldName]"></mt-cell>
                    </div>
                    <div class="mint-cell-wrapper">&nbsp;
                    </div>
                </div>
                <!-- 提示区域(可自定义颜色位置 写在 tipposution) -->
                <p slot="loading" class="tipposution">加载中...</p>
                <p slot="ending" class="tipposution">没有数据了</p>
                <p slot="failed" class="tipposution">数据加载失败</p>
                <p slot="nodata" class="tipposution">暂无数据</p>
            </my-infinite>
        </div>
    </mt-popup>
</template>

<script>
import listview from '@/components/mixins/listview';

export default {
  data() {
    return {
      showPanel: true,
      selectList: [],
    };
  },
  methods: {
    toggleSelectPanel(item) {
      if (item) {
        const obj = {};
        obj.targetValue = this.targetValue;
        obj.targetName = this.targetName;
        obj.id = this.id;
        obj.fieldName = this.fieldName;
        obj.item = item;
        this.$emit('selectValue', obj);
      }
      this.showPanel = !this.showPanel;
    },
    selectValue(item) {
      this.toggleSelectPanel(item);
    },
  },
  computed: {
    loadUrl() {
      return `/${this.name}/list`;
    },
  },
  props: ['name', 'id', 'fieldName', 'targetValue', 'targetName'],
  mixins: [listview],
};
</script>

<style scoped lang="stylus" rel="stylesheet/stylus">
    @import "../../../common/stylus/views/listview.styl"
</style>

