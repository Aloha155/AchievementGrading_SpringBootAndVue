<template>
  <div class="d-flex mt-5">
    <column-assist
      v-model="workOrderColumn"
      v-if="workOrderColumn"
    ></column-assist>
    <div class="flex-1" v-if="total">
      <el-pagination
        ref="pagination"
        layout="prev, pager, next, jumper"
        background
        :page-size="size"
        :total="total"
        :current-page="page"
        @current-change="changePage"
      >
      </el-pagination>
    </div>
  </div>
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
    },
    total: {
      type: Number
    },
    page: {
      type: Number
    },
    size: {
      type: Number
    }
  },
  data() {
    return { list: this.workOrderColumn }
  },
  methods: {
    change() {
      this.$emit("change", this.list)
    },
    changePage(value) {
      this.$emit("current-change", value)
    }
  }
}
</script>

<style scoped></style>
