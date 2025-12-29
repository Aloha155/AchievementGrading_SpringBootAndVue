<template>
  <div class="el-form-item">
    <label class="el-form-item__label">{{ label }}</label>
    <div class="el-form-item__content">
      <p>{{ value }}</p>
    </div>
  </div>
</template>

<script>
export default {
  componentName: "Desc",
  props: ["label", "value"]
}
</script>

<style type="text/less" lang="less" scoped>
@import "../../assets/style/color";
@import "../../assets/style/config";
@import "../../assets/style/mixin";
.el-form-item__label {
  float: none;
}
p {
  min-height: 40px;
  line-height: 40px;
  border-bottom: 1px solid @color-text-placeholder;
  color: @color-text-secondary;
}
</style>
