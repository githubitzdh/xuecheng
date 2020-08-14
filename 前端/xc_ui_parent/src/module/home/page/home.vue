<template>
  <el-container class="container">
    <el-header class="header">
      <el-col :span="24" class="header">
        <el-col :span="14" class="logo">
          <img src="/static/images/asset-logoIco-white.png" width="500px" alt />
        </el-col>
      </el-col>
    </el-header>
    <el-container>
      <el-aside width="200px">
        <!-- 导航菜单 start -->
        <el-menu
          default-active="0-0"
          class="el-menu-vertical-demo"
          :collapse="isCollapse"
        >
        <!-- 处理一级菜单start -->
          <el-submenu v-for="(item,ii) in menus" :key="ii" :index="ii+''">
            <template slot="title">
              <i class="el-icon-menu"></i>
              <span slot="title">{{item.name}} </span>
            </template>
            <!-- 二级菜单start -->
            <el-menu-item v-for="(child,ci) in item.children.filter(childItem=>childItem.hidden == false)" :key="ci"  :index="ii+'-'+ci">
              <router-link :to="child.path">{{child.name}} </router-link>
            </el-menu-item>
            <!-- 二级菜单end -->
          </el-submenu>
            <!-- 一级菜单end -->
        </el-menu>
        <!-- 导航菜单 end -->
      </el-aside>
      <el-main class="main">
        <!-- 子路由显示位置 -->
        <router-view></router-view>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
  export default {
    data() {
      return {
        isCollapse: false, // 是否折叠
        menus: [] // 一级菜单
      }
    },
    created() {
      // 获得所有的路由,并过滤"隐藏"
      // options需要去前端控制台展示查看数据
      this.menus = this.$router.options.routes.filter(item => !item.hidden)
    }
  }
</script>

<style lang="scss">
.container {
  height: 100%;
  .header {
    height: 60px;
    padding: 0;
    line-height: 60px;
    background: #0660a3;
    color: #fff;
    .logo {
      height: 60px;
      font-size: 20px;
      border-color: rgba(238, 241, 146, 0.3);
      img {
        width: 200px;
        float: left;
        margin: 10px 10px 10px 18px;
      }
      .txt {
        color: #fff;
      }
    }
  }
  .main {
    background-color: #e9eef3;
    color: #333;
  }
}
</style>
