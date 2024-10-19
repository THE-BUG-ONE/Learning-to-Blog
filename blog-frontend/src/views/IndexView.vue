<script setup>
import {logout} from '@/net'
import router from "@/router/index.js";

const userLogout = () => {
  logout(() => router.push('/'))
}
</script>

<template>
  <div>
    这里是主页面
  </div>
  <div>
    <el-button @click="userLogout()" type="danger" plain>退出登录</el-button>
  </div>
</template>

<style scoped>

</style>