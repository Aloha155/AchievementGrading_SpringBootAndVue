<template>
  <el-popover>
    <el-button slot="reference" size="mini">可视列编辑</el-button>
    <div v-for="item in list" :key="item.prop">
      <el-checkbox v-model="item.visible" class="ma-2" @change="change">{{
        item.label
      }}</el-checkbox>
    </div>
  </el-popover>
</template>

<script>
export default {
  model: {
    event: "change",
    prop: "workOrderColumn"
  },
  props: {
    workOrderColumn: {
      type: Array
    }
  },
  data() {
    return { list: this.workOrderColumn }
  },
  methods: {
    change() {
      this.$emit("change", this.list)
    }
  }
}
</script>

<style scoped></style>
