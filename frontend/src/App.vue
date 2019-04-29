<template>
  <v-app >
    <v-toolbar app>
      <v-toolbar-title class="headline text-uppercase">
        <span>Moj Broj</span>
      </v-toolbar-title>
      <v-spacer></v-spacer>
      <v-btn flat>
        <span class="mr-2">Version 1.0</span>
      </v-btn>
    </v-toolbar>
    <v-content>
      <SolverForm/>
    </v-content>
  </v-app>
</template>

<script>
import SolverForm from "./components/SolverForm";

export default {
  name: "App",
  components: {
    SolverForm
  },
  data() {
    return {
      //
    };
  }
};
</script>
