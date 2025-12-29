<template>
  <div class="footer-box"></div>
</template>

<script>
export default {
  componentName: "footer",
  computed: {},
  methods: {}
}
</script>

<style type="text/less" lang="less" scoped>
@import "../../assets/style/color";
@import "../../assets/style/config";
@import "../../assets/style/mixin";

.footer-box {
  background-color: black;
  height: @footer-height;
  display: flex;
  align-items: center;
}
</style>
