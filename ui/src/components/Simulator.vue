<script>
import { getCurrentBoard, resetBoard } from './board.api';
import { getAllCells } from './cells.api';
import { getAllFires, createFires, nextFires } from './fires.api';

import Grid from './Grid.vue'

export default {
  components: { Grid },
  data() {
    return {
      isLoading: false,
      h: 0,
      w: 0,
      items: [],
      selectedCells: [],
      newWidth: 0,
      newHeight: 0,
    };
  },
  computed: {
    // a computed getter
    myComputedContent() {

      console.log("computed()");
      return this.items;
    },
  },
  async mounted() {
    console.log("mounted()");
    // set loading screen
    this.isLoading = true;
    await this.fetchBoard();
    this.isLoading = false;
  },
  emits: ['update:h', 'update:w'],
  methods: {
    callReset() {
      console.log("call Reset : " + this.newWidth + ", " + this.newHeight);
      this.resetBoard();
      this.resetCells();
      this.resetSelectedCells();
    },
    callNextStep() {
      console.log("call NextStep");
      this.nextStep();
    },
    resetSelectedCells() {
      this.selectedCells = [];
    },
    callNextWidth(value) {
      console.log("call NextWidth : " + value);
      this.newWidth = value;
    },
    callNextHeight(value) {
      console.log("call NextHeight : " + value);
      this.newHeight = value;
    },
    handleMessage(message) {
      if (this.selectedCells.includes(message)) {
        console.log("do not handleMessage : ", message);
        return;
      }
      console.log("handleMessage : ", message);
      this.selectedCells.push(message);
    },
    async resetCells() {

      let array = Array(this.newHeight).fill(Array(this.newWidth));
      console.log(array);
      for(let i=0;i<this.newHeight;i++) {
        for(let j=0; j<this.newWidth; j++) {
          console.log(">> array[" + j +"][" + i + "]", array[j], array[j][i]);
          array[j][i] = {
            x: j,
            y: i,
            value: "array[" + j +"][" + i + "]",
            isAlive: true,
            isBurning: false,
            isDead: false
          };
        }
      }
      this.items = array;
      console.log(array);

      this.w = this.newWidth;
      this.h = this.newHeight;
    },
    async addFires() {
      const response = await createFires(false, this.selectedCells);
      console.log(response);
      response.data.forEach(function (item, index, array) {
        console.log(item, index);
        this.items[item.y][item.x].isAlive = !item.isBurning && !item.isDead;
        this.items[item.y][item.x].isBurning = item.isBurning;
        this.items[item.y][item.x].isBurning = item.isDead;
      });
      this.selectedCells = [];
    },
    async resetBoard({commit}) {
      try {
        const response = await resetBoard(this.newWidth, this.newHeight);
        console.log(response);
        commit(response.data);
      } catch (error) {
        // handle the error here
      }
    },
    async fetchBoard() {
      const response = await getCurrentBoard();
      console.log(response);
      this.h = response.data.height;
      this.w = response.data.width;
    },
    async fetchCells() {
      const response = await getAllFires();
      console.log(response);
      response.data.forEach(function (item, index, array) {
        console.log(item, index);
        this.items[item.y][item.x].isAlive = !item.isBurning && !item.isDead;
        this.items[item.y][item.x].isBurning = item.isBurning;
        this.items[item.y][item.x].isBurning = item.isDead;
      });
    },
    async nextStep() {
      const response = await nextFires(false);
      console.log(response);
      response.data.forEach(function (item, index, array) {
        console.log(item, index);
        this.items[item.y][item.x].isAlive = !item.isBurning && !item.isDead;
        this.items[item.y][item.x].isBurning = item.isBurning;
        this.items[item.y][item.x].isBurning = item.isDead;
      });
    }
  }
}
</script>

<template>
    <div class="simulator">
        <h1 class="green">Simulateur de propagation d'incendies</h1>
        <Grid v-model="items" />
        <div>
          <span>W: </span><input type="text"
                                 :value="w"
                                 maxlength="2" size="2"
                                 @input="callNextWidth($event.target.value)" />
          <span>H: </span><input type="text"
                                 :value="h"
                                 maxlength="2" size="2"
                                 @input="callNextHeight($event.target.value)" />
          <button @click="callReset()">reset</button>
          <button @click="callNextStep()">next</button>
        </div>
    </div>
</template>

<style>
.simulator h1,
.simulator h3 {
  text-align: center;
}
</style>
