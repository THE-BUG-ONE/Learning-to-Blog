<script setup>

</script>

<template>
  <div style="width: 100vw;height: 100vh;overflow: hidden;display: flex">
    <div style="width: 30%;background-color: var(--el-bg-color);z-index: 2;">
      <router-view v-slot="{ Component }">
        <transition name="el-fade-in-linear" mode="out-in">
          <component :is="Component" style="height: 100%"/>
        </transition>
      </router-view>
    </div>
    <div class="welcome-title">
      <div style="font-size: 30px;font-weight: bold">这里是标题balabala</div>
      <div style="font-size: 14px">这里是第一条网站介绍balabala</div>
      <div style="font-size: 9px">这里是第二条网站补充介绍balabala</div>
    </div>
    <div style="flex:1">
      <el-image style="width: 100%;height: 100%" fit="cover"
                src="https://img.zcool.cn/community/013357596da4a2a8012193a396f848.jpg@2o.jpg"/>
    </div>
  </div>
</template>

<style scoped>
.welcome-title {
  position: absolute;
  bottom: 5%;
  right: 3%;
  color: white;
  text-shadow: 0 0 10px black;
  z-index: 1;
  text-align: right;
}
</style>