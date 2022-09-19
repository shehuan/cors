<template>
  <div id="app">
<!--    <img alt="Vue logo" src="./assets/logo.png">-->
    <button @click="login">login</button>
    <button @click="hello">hello</button>
  </div>
</template>

<script>
import {getRequest, postKeyValueRequest} from "@/utils/api";

export default {
  name: 'App',
  data() {
    return {
      info: {
        username: 'zhangsan',
        password: '123456'
      }
    }
  },
  methods: {
    hello() {
      getRequest("http://127.0.0.1:8080/hello").then(data => {
        console.log("success---" + data);
      }).catch(error => {
        console.log("error---" + error);
      })
    },
    login() {
      postKeyValueRequest("http://127.0.0.1:8080/doLogin", this.info).then(data => {
        console.log("success---" + data);
      }).catch(error => {
        console.log("error---" + error);
      })
    }
  }
}
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style>
