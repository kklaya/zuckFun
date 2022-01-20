<template>
  <div class="hello">
        <span>青春是一个短暂的美梦, 当你醒来时, 它早已消失无踪</span>
    <el-divider></el-divider>
    <el-calendar v-model="value">
</el-calendar>
  </div>

</template>

<script>
export default {
  name: 'HelloWorld',
  data () {
    return {
      msg: 'Welcome to Your Vue.js App',
      value: new Date()
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
h1, h2 {
  font-weight: normal;
}
ul {
  list-style-type: none;
  padding: 0;
}
li {
  display: inline-block;
  margin: 0 10px;
}
a {
  color: #42b983;
}
</style>
