<template>
  <i v-if="modelValue.isDead" class="black bi bi-tree"></i>
  <i v-else-if="modelValue.isBurning" class="red bi bi-fire"></i>
  <i v-else class="green bi bi-tree-fill" @click="cellClickEvent($event)"></i>
</template>

<script lang="ts">
import "bootstrap-icons/font/bootstrap-icons.css"
import "bootstrap/dist/js/bootstrap.js"

export default {
  props: {
    modelValue: {
      type: Object,
      required: true }
  },
  emits: ['clickEvent'],
  methods: {
    cellClickEvent(event) {
      console.log('Custom cell click > ...', {x: this.modelValue.x, y: this.modelValue.y});
      this.modelValue.isAlive = false;
      this.modelValue.isBurning = true;
      this.$emit('clickEvent', {x: this.modelValue.x, y: this.modelValue.y});
    }
  }
}
</script>

<style>
i {
  display: block;
  vertical-align: middle;
  align: middle;
}
i:hover {
  cursor: pointer;
}
i .green {
  background-color: green
}
i .red {
  background-color: red
}
i .black {
  background-color: black
}
</style>
