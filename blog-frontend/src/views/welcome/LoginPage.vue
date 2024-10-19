<script setup>
import {User, Lock} from '@element-plus/icons-vue'
import {reactive, ref} from "vue"
import {login} from "@/net/index.js"
import router from "@/router/index.js"

const formRef = ref()

const form = reactive({
  username: 'admin',
  password: '123456',
  remember: false
})

const rules = {
  username: [
    {required: true, message: '请输入用户名'}
  ],
  password: [
    {required: true, message: '请输入密码'}
  ]
}

function userLogin() {
  formRef.value.validate((valid) => {
    if (valid) {
      login(
          form.username,
          form.password,
          form.remember,
          () => router.push({path:'/index'})
      )
      console.info('用户登录')
    }
  })
}
</script>

<template>
  <div style="text-align: center;margin: 0 10%">
    <div style="margin-top: 30%">
      <div style="font-size: 25px;font-weight: bold">登录</div>
      <div style="font-size: 14px;color: darkgrey">请先输入用户名和密码进行登录</div>
    </div>
    <div style="margin-top: 10%">
      <el-form :model="form" :rules="rules" ref="formRef">
        <el-form-item prop="username">
          <el-input v-model="form.username" type="text" maxlength="8" placeholder="用户名/邮箱">
            <template #prefix>
              <el-icon><User /></el-icon>
            </template>
          </el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input v-model="form.password" type="password" maxlength="16" style="margin-top: 10px;" placeholder="密码">
            <template #prefix>
              <el-icon><Lock /></el-icon>
            </template>
          </el-input>
        </el-form-item>
        <el-row style="margin-top: 10px">
          <el-col :span="12" style="text-align: left">
            <el-form-item>
              <el-checkbox v-model="form.remember" label="记住我"/>
            </el-form-item>
          </el-col>
          <el-col :span="12" style="text-align: right;padding-top: 2px">
            <el-link @click="router.push('/forget')">忘记密码？</el-link>
          </el-col>
        </el-row>
      </el-form>
    </div>
    <div style="margin-top: 40px">
      <el-button @click="userLogin()" style="width: 80%" size="large" type="success" plain>立即登录</el-button>
    </div>
    <el-divider>
      <span style="color: darkgrey">没有账号</span>
    </el-divider>
    <div>
      <el-button @click="router.push('/register')" style="width:80%" size="large" type="warning" plain>注册账号</el-button>
    </div>
  </div>
</template>

<style scoped>

</style>