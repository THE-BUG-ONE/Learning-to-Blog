<script setup>
import axios from "axios";
import {get} from "@/net/index.js";


function getArticleList() {
  get('article/article-list', (data) => {
    for (let datum of data) {
      console.info(datum)
    }
  }, () => {})
}

function getHotArticleList() {
  get('article/hot-article-list', (data) => {
    for (let datum of data) {
      console.info(datum)
    }
  }, () => {})
}
</script>

<template>
  <el-button @click="getArticleList" type="success" plain>getArticleList()</el-button>
  <el-button @click="getHotArticleList" type="success" plain>getHotArticleList()</el-button>
</template>

<style scoped>
</style>
