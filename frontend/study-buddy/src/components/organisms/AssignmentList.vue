<!-- <template>
  <div class="ion-padding">
    <ion-list>
      <assignment-list-item
        v-for="item in assignments"
        :key="item.id"
        :id="item.id"
        :title="item.title"
        :deadline="item.deadline"
      ></assignment-list-item>
    </ion-list>
  </div>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import { IonList } from "@ionic/vue";
import AssignmentListItem from "@/components/molecules/AssignmentListItem.vue";

export default defineComponent({
  name: "AssignmentList",
  components: {
    IonList,
    AssignmentListItem,
  },
  props: ["assignments"],
  data() {
    return {};
  },
});
</script> -->

<template>
  <div class="ion-padding">
    <ion-list>
      <assignment-list-item
        v-for="item in assignments"
        :key="item.id"
        :id="item.id"
        :title="item.title"
        :deadline="item.deadline"
        @delete="$emit('delete', $event)"
        @edit="$emit('edit', $event)"
        @view-assignment="$emit('view-assignment', $event)"
      ></assignment-list-item>
    </ion-list>
  </div>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import { IonList } from "@ionic/vue";
import AssignmentListItem from "@/components/molecules/AssignmentListItem.vue";

export default defineComponent({
  name: "AssignmentList",
  components: {
    IonList,
    AssignmentListItem,
  },
  props: ["assignments"],
  emits: ["edit", "delete", "view-assignment"],
});
</script>
