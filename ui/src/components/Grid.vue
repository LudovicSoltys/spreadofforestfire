<template>
  <div class="board">
    <table class="board">
      <tr v-for="j in h">
        <td v-for="i in w">
          <Cell v-model="items[j-1][i-1]" @clickEvent="handleMessage"/>
        </td>
      </tr>
    </table>
  </div>
</template>

<script>
import Cell from './Cell.vue'
</script>

<style>

</style>
