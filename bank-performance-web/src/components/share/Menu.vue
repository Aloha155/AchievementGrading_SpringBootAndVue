<template>
  <el-menu :default-active="active" :collapse="collapse" router unique-opened>
    <template v-for="item in menuList">
      <el-submenu v-if="item.child" :index="item.label" :key="item.path">
        <template slot="title">
          <i :class="item.icon"></i>
          <span>{{ item.label }}</span>
        </template>
        <el-menu-item-group>
          <el-menu-item
            v-for="sub in item.child"
            :index="sub.path"
            :key="sub.path"
            >{{ sub.label }}</el-menu-item
          >
        </el-menu-item-group>
      </el-submenu>
      <el-menu-item v-else :index="item.path" :key="item.path">
        <i :class="item.icon"></i>
        <span slot="title">{{ item.label }}</span>
      </el-menu-item>
    </template>
  </el-menu>
</template>

<script>
import { mapState } from "vuex"
import { menuListConstant } from "../../assets/script/constant"
export default {
  name: "Menu",
  computed: {
    ...mapState("user",["user"]),
    ...mapState("menu", ["collapse"]),
    active() {
      let subMenuList = this.menuList.map((item) => item.child);
      subMenuList = subMenuList.concat(this.menuList).flat();
      return subMenuList
        .filter((item) => this.$route.fullPath.indexOf(item?.path) === 0)
        .sort((a, b) => b.path.length - a.path.length)[0].path
    }
  },
  data() {
    return {
      menuList: menuListConstant()
    }
  },
  mounted() {}
}
</script>

<style type="text/less" lang="less" scoped></style>
