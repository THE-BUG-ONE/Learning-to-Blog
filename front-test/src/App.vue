<script setup>
import {get} from "@/net/index.js";


function getArticleList() {
  get('article/article-list', (data) => {
    console.info(data)
  }, () => {})
}

function getHotArticleList() {
  get('article/hot-article-list', (data) => {
    console.info(data)
  }, () => {})
}

function getCategoryList() {
  get('category/list', (data) => {
    console.info(data)
  }, () => {})
}
</script>

<template>
  <el-button @click="getArticleList" type="success" plain>getArticleList()</el-button>
  <el-button @click="getHotArticleList" type="success" plain>getHotArticleList()</el-button>
  <el-button @click="getCategoryList" type="success" plain>getCategoryList()</el-button>
</template>

<style scoped>
</style>
