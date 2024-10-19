<script setup>
import {reactive, ref} from "vue";
import {EditPen, Lock, Message} from "@element-plus/icons-vue";
import {get, post} from "@/net/index.js";
import {ElMessage} from "element-plus";
import router from "@/router/index.js";

const active = ref(0)
const formRef = ref()
const coldTime = ref(0)
const isEmailValid = (() => /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/.test(form.email))

const form = reactive({
  password: '',
  password_repeat: '',
  email: '',
  code: ''
})

const validatePassword = (rule, value, callback) => {
  if (value === '') {
    callback(new Error('清再次输入密码'))
  } else if (value !== form.password) {
    callback(new Error('两次输入的密码不一致'))
  } else {
    callback()
  }
}

function askCode() {
  if (isEmailValid) {
    coldTime.value = 60
    get(`/api/auth/ask-code?email=${form.email}&type=reset`, () => {
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

function confirmReset() {
  formRef.value.validate((isValid) => {
    if (isValid) {
      post('api/auth/reset-confirm', {
        email: form.email,
        code: form.code
      }, () => {
        active.value++
      })
    } else {
      ElMessage.warning('请输入正确的邮箱地址和验证码')
    }
  })
}

function doReset() {
  formRef.value.validate((isValid) => {
    if (isValid) {
      post('api/auth/reset-password', {...form}, () => {
        ElMessage.success('密码重置成功，请重新登录')
        router.push("/")
      })
    } else {
      ElMessage.warning('请填写新的密码')
    }
  })
}

const rules = {
  password: [
    { required: true, message: '请输入密码', trigger: 'blur'},
    { min: 6, max: 16, message: '长度必须在6-16个字符之间', trigger: ['blur']}
  ],
  password_repeat: [
    { validator: validatePassword, trigger: ['blur', 'change']}
  ],
  email: [
    { required: true, message: '请输入邮箱地址', trigger: 'blur'},
    { type: 'email', message: '请输入合法的电子邮箱地址', trigger: ['blur', 'change']}
  ],
  code: [
    { required: true, message: '请输入获取的验证码', trigger: 'blur'}
  ]
}
</script>

<template>
  <div>
    <div style="margin: 10% 10%">
      <el-steps :active="active" finish-status="success" align-center>
        <el-step title="验证邮箱地址"/>
        <el-step title="重新设置密码"/>
      </el-steps>
    </div>
    <transition name="el-fade-in-linear" mode="out-in">
      <div style="text-align: center;margin: 0 10%;height: 100%" v-if="active === 0">
        <div style="margin-top: 10%">
          <div style="font-size: 25px;font-weight: bold">重置密码</div>
          <div style="font-size: 14px;color: darkgrey">请输入账户验证邮箱地址</div>
        </div>
        <div style="margin-top: 15%">
          <el-form :model="form" :rules="rules" ref="formRef">
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
        <div style="margin-top: 5%">
          <span style="font-size: 14px;line-height: 15px;color: darkgrey">已有帐号?&nbsp;</span>
          <el-link type="warning" style="translate: 0 -2px" @click="router.push('/')">立即登录</el-link>
        </div>
        <div style="margin-top: 30%">
          <el-button style="width: 80%" size="large" type="danger" @click="confirmReset" plain>下一步</el-button>
        </div>
      </div>
    </transition>
    <transition name="el-fade-in-linear" mode="out-in">
      <div style="text-align: center;margin: 0 10%;height: 100%" v-if="active === 1">
        <div style="margin-top: 10%">
          <div style="font-size: 25px;font-weight: bold">重置密码</div>
          <div style="font-size: 14px;color: darkgrey">请输入新密码并重复验证</div>
        </div>
        <div style="margin-top: 10%">
          <el-form :model="form" :rules="rules" ref="formRef">
            <el-form-item prop="password">
              <el-input v-model="form.password" :maxlength="16" type="password" placeholder="新密码">
                <template #prefix>
                  <el-icon><Lock/></el-icon>
                </template>
              </el-input>
            </el-form-item>
            <el-form-item prop="password_repeat">
              <el-input v-model="form.password_repeat" :maxlength="16" type="password" placeholder="重复新密码">
                <template #prefix>
                  <el-icon><Lock/></el-icon>
                </template>
              </el-input>
            </el-form-item>
          </el-form>
        </div>
        <div style="margin-top: 10%">
          <el-button style="width: 80%" size="large" type="danger" @click="doReset" plain>立即重置密码</el-button>
        </div>
        <el-divider>
          <span style="color: darkgrey">没有账号</span>
        </el-divider>
        <div>
          <el-button style="width: 80%" size="large" type="default" @click="active = 0" plain>返回上一步</el-button>
        </div>
      </div>
    </transition>
  </div>
</template>

<style scoped>

</style>