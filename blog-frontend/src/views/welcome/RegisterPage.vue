<script setup>

import {EditPen, Lock, Message, User} from "@element-plus/icons-vue";
import router from "@/router/index.js";
import {reactive, ref} from "vue";
import {ElMessage} from "element-plus";
import {get, post} from "@/net/index.js";

const form = reactive({
  username: '',
  password: '',
  password_repeat: '',
  email: '',
  code: ''
})

const validateUsername = (rule, value, callback) => {
  if (value === '') {
    callback(new Error('请输入用户名'))
  } else if (!/^[a-zA-Z0-9\u4e00-\u9fa5]+$/.test(value)) {
    callback(new Error('用户名不能包含特殊字符'))
  } else {
    callback()
  }
}

const validatePassword = (rule, value, callback) => {
  if (value === '') {
    callback(new Error('清再次输入密码'))
  } else if (value !== form.password) {
    callback(new Error('两次输入的密码不一致'))
  } else {
    callback()
  }
}

const rules = {
  username: [
    { validator: validateUsername, trigger: ['blur', 'change']},
    { min: 2, max: 8, message: '长度必须在2-8个字符之间', trigger: ['blur']}
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur'},
    { min: 6, max: 16, message: '长度必须在6-16个字符之间', trigger: ['blur']}
  ],
  password_repeat: [
    { validator: validatePassword, trigger: ['blur', 'change']}
  ],
  email: [
    { required: true, message: '请输入邮箱地址', trigger: 'blur'},
    { type: 'email', message: '请输入合法的电子邮箱地址', trigger: ['blur']}
  ],
  code: [
    { required: true, message: '请输入获取的验证码', trigger: 'blur'}
  ]
}
const formRef = ref()

const coldTime = ref(0)

const isEmailValid = (() => /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/.test(form.email))

function askCode() {
  if (isEmailValid) {
    coldTime.value = 60
    get(`/api/auth/ask-code?email=${form.email}&type=register`, () => {
      ElMessage.success('验证码已发送到邮箱，请注意查收')
      setInterval(() => coldTime.value--, 1000)
    }, (message) => {
      ElMessage.warning(message)
      coldTime.value = 0
    });
  } else {
    ElMessage.warning('请输入正确的电子邮箱地址')
  }
}

const register = () => {
  formRef.value.validate((isValid) => {
    if (isValid) {
      post('/api/auth/register', {...form}, () => {
        ElMessage.success('注册成功')
        router.push('/')
      })
    } else {
      ElMessage.warning('请完整填写表单内容')
    }
  })
}

</script>

<template>
  <div style="text-align: center;margin: 0 10%">
    <div style="margin-top: 20%">
      <div style="font-size: 25px;font-weight: bold">注册新用户</div>
      <div style="font-size: 14px;color: darkgrey">请先填写相关信息进行注册</div>
    </div>
    <div style="margin-top: 10%">
      <el-form :model="form" :rules="rules" ref="formRef">
        <el-form-item prop="username">
          <el-input v-model="form.username" :maxlength="8" type="text" placeholder="用户名">
            <template #prefix>
              <el-icon><User/></el-icon>
            </template>
          </el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input v-model="form.password" :maxlength="16" type="password" placeholder="密码">
            <template #prefix>
              <el-icon><Lock/></el-icon>
            </template>
          </el-input>
        </el-form-item>
        <el-form-item prop="password_repeat">
          <el-input v-model="form.password_repeat" :maxlength="16" type="password" placeholder="重复密码">
            <template #prefix>
              <el-icon><Lock/></el-icon>
            </template>
          </el-input>
        </el-form-item>
        <el-form-item prop="email">
          <el-input v-model="form.email" type="email" placeholder="电子邮箱地址">
            <template #prefix>
              <el-icon><Message/></el-icon>
            </template>
          </el-input>
        </el-form-item>
        <el-form-item prop="code">
          <el-row :gutter="10" style="width: 100%">
            <el-col :span="15">
              <el-input v-model="form.code" :maxlength="6" type="text" placeholder="请输入验证码">
                <template #prefix>
                  <el-icon><EditPen/></el-icon>
                </template>
              </el-input>
            </el-col>
            <el-col :span="5">
              <el-button type="primary" :disabled="!isEmailValid || coldTime > 0" @click="askCode" plain>
                {{coldTime > 0 ? '请稍候' + coldTime + '秒' : '获取验证码'}}
              </el-button>
            </el-col>
          </el-row>
        </el-form-item>
      </el-form>
    </div>
    <div style="margin-top: 10%">
      <el-button style="width: 80%" size="large" type="success" @click="register" plain>立即注册</el-button>
    </div>
    <div style="margin-top: 5%">
      <span style="font-size: 14px;line-height: 15px;color: darkgrey">已有帐号?&nbsp;</span>
      <el-link type="warning" style="translate: 0 -2px" @click="router.push('/')">立即登录</el-link>
    </div>
  </div>
</template>

<style scoped>

</style>