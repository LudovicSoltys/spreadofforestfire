<template>
  <div class="board">
    <div v-for="j in 6">
      <div v-for="i in 5">
        <span>{{ i }} - {{ j }}</span>
        <Cell />
      </div>
    </div>
  </div>
</template>

<script>
import Cell from './Cell.vue'
</script>

<style>

</style>
