<template>
  <v-container>
    <v-layout text-xs-center wrap>
      <v-flex xs12>
        <v-img :src="require('../assets/logo.png')" class="my-3" contain height="200"></v-img>
      </v-flex>

      <v-flex mb-4>
        <h1 class="display-2 font-weight-bold mb-3">Welcome to Moj Broj</h1>
        <p class="subheading font-weight-regular">
          Web application for solving number puzzles.
          <br>Inspired by one of the games from the popular Serbian TV quiz "Slagalica".
        </p>
      </v-flex>

      <!--Puzzle form-->
      <v-flex mb-1 xs12>
        <h2 class="headline font-weight-bold mb-3">Enter The Puzzle</h2>
        <v-form v-model="valid">
          <v-container>
            <v-layout justify-center>
              <v-flex xs4 sm2 md1>
                <v-text-field type="number" min="1" max="9" v-model="nr1" label="1-9" outline></v-text-field>
              </v-flex>
              <v-flex xs4 sm2 md1>
                <v-text-field
                  type="number"
                  min="1"
                  max="9"
                  v-model="nr2"
                  label="1-9"
                  required
                  outline
                ></v-text-field>
              </v-flex>
              <v-flex xs4 sm2 md1>
                <v-text-field
                  type="number"
                  min="1"
                  max="9"
                  v-model="nr3"
                  label="1-9"
                  required
                  outline
                ></v-text-field>
              </v-flex>
              <v-flex xs4 sm2 md1>
                <v-text-field
                  type="number"
                  min="1"
                  max="9"
                  v-model="nr4"
                  label="1-9"
                  required
                  outline
                ></v-text-field>
              </v-flex>
              <v-flex xs4 sm4 md2 lg2 xl1>
                <v-text-field
                  type="number"
                  min="10"
                  max="20"
                  v-model="nr5"
                  label="10, 15, 20"
                  required
                  outline
                ></v-text-field>
              </v-flex>
              <v-flex xs4 sm4 md3 lg3 xl2>
                <v-text-field
                  type="number"
                  min="25"
                  max="100"
                  v-model="nr6"
                  label="25, 50, 75, 100"
                  required
                  outline
                ></v-text-field>
              </v-flex>
              <v-flex xs4 sm4 md2 lg2 xl1>
                <v-text-field
                  type="number"
                  min="1"
                  max="999"
                  v-model="target"
                  label="Target"
                  required
                  outline
                ></v-text-field>
              </v-flex>
            </v-layout>
            <v-btn color="primary" large @click="submit">solve</v-btn>
            <v-btn color="error" large @click="clear">clear</v-btn>
          </v-container>
        </v-form>
      </v-flex>
      <!-- Solutions -->

      <v-flex mb-1 xs12>
        <transition name="fade">
          <div v-if="displaySolutions">
            <v-container v-if="hasSolutions()">
              <h2 class="headline font-weight-bold mb-3">Solutions</h2>
              <transition-group name="list" tag="p">
                <h2 v-for="(solution, index) in solutions" v-bind:key="index">{{solution}}</h2>
              </transition-group>
            </v-container>
            <v-container v-else>
              <h2
                class="headline font-weight-bold mb-3"
              >Sorry, there are no solutions for the given expression.</h2>
            </v-container>
          </div>
        </transition>
      </v-flex>
    </v-layout>
  </v-container>
</template>

<script>
import { validationMixin } from "vuelidate";
import { required, between, numeric } from "vuelidate/lib/validators";
import { setTimeout, setInterval } from "timers";
import { constants } from "crypto";
export default {
  mixins: [validationMixin],

  validations: {
    nr1: { numeric, between: between(1, 9) },
    nr2: { numeric, between: between(1, 9) },
    nr3: { numeric, between: between(1, 9) },
    nr4: { numeric, between: between(1, 9) },
    nr5: { numeric, between: between(10, 20) },
    nr6: { numeric, between: between(25, 100) },
    target: { numeric, required, between: between(1, 999) }
  },

  data: () => ({
    valid: Boolean,
    nr1: 1,
    nr2: 1,
    nr3: 1,
    nr4: 1,
    nr5: 10,
    nr6: 25,
    target: 1,
    solutions: [],
    displaySolutions: false,
    serverOrigin: "http://localhost:3030"
  }),

  methods: {
    submit: function() {
      //Testing 
      // this.solutions = [];

      let numbersArr = [];
      numbersArr.push(
        this.nr1,
        this.nr2,
        this.nr3,
        this.nr4,
        this.nr5,
        this.nr6
      );
      this.target = parseInt(this.target);
      let body = {
        numbers: numbersArr,
        target: this.target
      };

      this.$http
        .post(this.serverOrigin + "/api/v1/solve", body)
        .then(function(data) {
          if (data.body.solutions.length > 0) {
            //We have soultions
            data.body.solutions.forEach(solution => {
              this.addSolution(solution);
            });
          }
          this.displaySolutions = true;
        });

      this.solutions.forEach(solutions => {
        this.solutions.push(solutions + " <");
        console.log("cao");
      });

    },
    addSolution: function(solution) {
      this.solutions.push(solution);
    },
    hasSolutions: function() {
      return this.solutions.length > 0;
    },
    clear() {
      this.nr1 = 1;
      this.nr2 = 1;
      this.nr3 = 1;
      this.nr4 = 1;
      this.nr5 = 10;
      this.nr6 = 25;
      this.target = 1;
      this.solutions = [];
    }
  }
};
</script>

<style>
.input {
  width: 80% !important;
}
.smallInput {
  width: 60% !important;
}

.list-item {
  display: inline-block;
  margin-right: 10px;
}
.list-enter-active,
.list-leave-active {
  transition: all 1s;
}
.list-enter, .list-leave-to /* .list-leave-active below version 2.1.8 */ {
  opacity: 0;
  transform: translateY(30px);
}

.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.5s;
}
.fade-enter, .fade-leave-to /* .fade-leave-active below version 2.1.8 */ {
  opacity: 0;
}
</style>
