<template>
  <ion-item lines="full">
    <div class="icon-wrapper">
    <ion-icon :icon="getIconByTitle(title)" slot="start" />
    </div>
    <ion-label>
      <h2>{{ title }}</h2>
    </ion-label>
  </ion-item>
</template>

<script lang="ts">
import { defineComponent } from 'vue'
import { IonItem, IonLabel, IonIcon } from '@ionic/vue'
import { sparkles, server, rose, paw } from 'ionicons/icons'

export default defineComponent({
  name: 'CourseListItem',
  components: {
    IonItem,
    IonLabel,
    IonIcon
  },
  props: {
    id: { type: Number, required: true},
    title: { type: String, required: true },
  },
  data() {
    return { iconList: [sparkles, server, rose, paw ]}
  },
  methods: {
    getIconByTitle(title: String){
        var charNumber = 0;
        for (let i = 0; i < title.length; i++) {
            charNumber += title.charCodeAt(i)
        }
       
        return this.iconList[charNumber % this.iconList.length]
        
    }
  }
})
</script>

<style scoped>
h2 {
  font-weight: bold;
  margin: 0;
}

.icon-wrapper {
  background-color: var(--ion-color-primary);
  border-radius: 4px;
  padding: 8px;
  margin-right: 8px;
  display: inline-flex;
  align-items: center;
  justify-content: center;
  width: 32px;
  height: 32px;
}
ion-icon {
  color: white; /* Icon-Farbe */
  font-size: 20px;
}

ion-item{
    margin-bottom: 20px;
}

</style>