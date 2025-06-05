<template>
  <ion-item lines="full">
    <ion-icon :icon="getIconByTitle(title)" slot="start" />
    <ion-label>
      <h2>{{ title }}</h2>
      <p>{{ deadline }}</p>
    </ion-label>
  </ion-item>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import { IonItem, IonLabel, IonIcon } from "@ionic/vue";
import {
  globeOutline,
  eyedropOutline,
  hardwareChipOutline,
  fitnessOutline,
} from "ionicons/icons";

export default defineComponent({
  name: "AssignmentListItem",
  components: {
    IonItem,
    IonLabel,
    IonIcon,
  },
  props: {
    id: { type: Number, required: true },
    title: { type: String, required: true },
    deadline: { type: String, required: true },
    icon: { type: String, default: globeOutline },
  },
  data() {
    return {
      iconList: [
        globeOutline,
        eyedropOutline,
        hardwareChipOutline,
        fitnessOutline,
      ],
    };
  },
  methods: {
    getIconByTitle(title: string) {
      let charNumber = 0;
      for (let i = 0; i < title.length; i++) {
        charNumber += title.charCodeAt(i);
      }

      return this.iconList[charNumber % this.iconList.length];
    },
  },
});
</script>

<style scoped>
h2 {
  font-weight: bold;
  margin: 0;
}
p {
  margin: 0;
  font-size: 0.9em;
  color: var(--ion-color-medium);
}
</style>
