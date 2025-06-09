<template>
  <base-layout
    page-title="Course"
    :show-back-button="true"
    :default-back-link="'/tabs/course-list'"
  >
    <template #header-buttons>
      <ion-buttons slot="end">
        <ion-button
          fill="outline"
          color="primary"
          @click="goToCreateAssignment"
        >
          <ion-icon :icon="add" slot="start" />
          Add Assignment
        </ion-button>
      </ion-buttons>
    </template>

    <app-spinner v-if="loading" class="loading-spinner"></app-spinner>

    <!-- Course Description Section -->
    <div v-if="courseStore.currentCourse" class="course-content">
      <div class="course-header">
        <div class="course-title-wrapper">
          <h2 class="course-title">
            <ion-icon :icon="bookOutline" class="course-icon" />
            {{ courseStore.currentCourse.title }}
          </h2>
        </div>
        <div
          v-if="courseStore.currentCourse.description"
          class="course-description-wrapper"
        >
          <p class="course-description">
            {{ courseStore.currentCourse.description }}
          </p>
        </div>
      </div>
    </div>

    <!-- Assignments Section -->
    <div class="assignments-section">
      <div class="section-header">
        <h3 class="section-title">Assignments</h3>
        <div class="section-divider"></div>
      </div>

      <div class="assignments-content">
        <assignment-list
          v-if="assignments.length > 0"
          :assignments="assignments"
          @edit="handleEdit"
          @delete="confirmDelete"
          @view-assignment="goToAssignmentPage"
          class="assignment-list"
        />
        <div v-else-if="!loading" class="empty-state">
          <div class="empty-state-content">
            <ion-icon :icon="documentOutline" class="empty-icon"></ion-icon>
            <p class="empty-message">No assignments found for this course.</p>
            <p class="empty-submessage">
              Get started by creating your first assignment.
            </p>
          </div>
        </div>
      </div>
    </div>

    <confirm-dialog
      :visible="showDialog"
      :title="`${assignmentTitleToDelete}`"
      :message="`Are you sure you want to delete this assignment?`"
      @confirm="deleteAssignment"
      @cancel="showDialog = false"
    />
  </base-layout>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import AssignmentList from "@/components/organisms/AssignmentList.vue";
import { useCourseStore } from "@/stores/courseStore";
import { useAssignmentStore } from "@/stores/assignmentStore";
import AppSpinner from "@/components/atoms/AppSpinner.vue";
import ConfirmDialog from "@/components/atoms/ConfirmDialog.vue";
import { add, documentOutline, bookOutline } from "ionicons/icons";
import { Assignment } from "@/types/assignment";
import { IonButton, IonButtons, IonIcon } from "@ionic/vue";

export default defineComponent({
  name: "CoursePage",
  components: {
    AssignmentList,
    AppSpinner,
    ConfirmDialog,
    IonButtons,
    IonButton,
    IonIcon,
  },
  data() {
    return {
      courseStore: useCourseStore(),
      assignmentStore: useAssignmentStore(),
      add,
      documentOutline,
      bookOutline,
      assignmentIdToDelete: null as number | null,
      assignmentTitleToDelete: "",
      showDialog: false,
    };
  },
  computed: {
    assignments() {
      return this.courseStore.currentCourseAssignments;
    },
    courseTitle() {
      return this.courseStore.currentCourse?.title ?? "Course";
    },
    loading() {
      return this.courseStore.isAssignmentListLoading;
    },
    courseId(): number {
      return Number(this.$route.params.id);
    },
  },
  async mounted() {
    await this.loadCourseData();
  },
  watch: {
    "$route.params.id"() {
      // Only load course data if we're actually on a course page
      if (
        this.$route.name === "CoursePage" ||
        this.$route.path.startsWith("/tabs/course/")
      ) {
        this.loadCourseData();
      }
    },
  },
  methods: {
    async loadCourseData() {
      const id = this.getValidCourseId();
      if (id === null) {
        this.$router.replace("/tabs/course-list");
        return;
      }

      await this.courseStore.selectCourse(id);
      if (this.courseStore.currentCourse) {
        await this.courseStore.listAllAssignmentsOfCurrentCourse();
      }
    },
    getValidCourseId(): number | null {
      const id = Number(this.$route.params.id);
      return isNaN(id) ? null : id;
    },
    goToCreateAssignment() {
      // Navigate to create assignment with pre-selected course
      this.$router.push(`/tabs/assignment/new?courseId=${this.courseId}`);
    },
    handleEdit(assignmentId: number) {
      this.$router.push({
        path: `/tabs/assignment/${assignmentId}/edit`,
        query: { from: `/tabs/course/${this.courseId}` },
      });
    },
    goToAssignmentPage(assignmentId: number) {
      this.$router.push({
        path: `/tabs/assignment/${assignmentId}/tasks`,
        query: { from: `/tabs/course/${this.courseId}` },
      });
    },
    confirmDelete(assignmentId: number) {
      const assignment = this.assignments.find(
        (a: Assignment) => a.id === assignmentId
      );
      if (assignment) {
        this.assignmentIdToDelete = assignmentId;
        this.assignmentTitleToDelete = assignment.title;
        this.showDialog = true;
      }
    },
    async deleteAssignment() {
      if (this.assignmentIdToDelete !== null) {
        await this.assignmentStore.remove(this.assignmentIdToDelete);
        // Refresh the assignments for this course
        await this.courseStore.listAllAssignmentsOfCurrentCourse();
        this.assignmentIdToDelete = null;
        this.assignmentTitleToDelete = "";
        this.showDialog = false;
      }
    },
  },
});
</script>

<style scoped>
.course-icon {
  margin-right: 12px;
  font-size: 1.2em;
  vertical-align: middle;
}
.loading-spinner {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 200px;
}

.course-content {
  background: var(--ion-color-light);
  margin: 16px;
  border-radius: 12px;
  overflow: hidden;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
}

.course-header {
  padding: 24px;
}

.course-title-wrapper {
  margin-bottom: 12px;
}

.course-title {
  margin: 0;
  font-size: 1.75em;
  font-weight: 600;
  line-height: 1.3;
  color: var(--ion-color-dark);
}

.course-description-wrapper {
  background: transparent;
  padding: 0;
  border-radius: 0;
  margin-top: 16px;
}

.course-description {
  margin: 0;
  font-size: 1em;
  line-height: 1.6;
  color: var(--ion-color-medium-shade);
}

.assignments-section {
  margin: 16px;
}

.section-header {
  margin-bottom: 20px;
}

.section-title {
  margin: 0 0 8px 0;
  font-size: 1.5em;
  font-weight: 700;
  color: var(--ion-color-dark);
}

.section-divider {
  height: 3px;
  background: linear-gradient(
    90deg,
    var(--ion-color-primary) 0%,
    var(--ion-color-primary-tint) 100%
  );
  border-radius: 2px;
  width: 60px;
}

.assignments-content {
  background: var(--ion-color-light);
  border-radius: 12px;
  overflow: hidden;
}

.assignment-list {
  background: transparent;
}

.empty-state {
  padding: 48px 24px;
  text-align: center;
}

.empty-state-content {
  max-width: 280px;
  margin: 0 auto;
}

.empty-icon {
  font-size: 4em;
  color: var(--ion-color-medium);
  margin-bottom: 16px;
  opacity: 0.6;
}

.empty-message {
  margin: 0 0 8px 0;
  font-size: 1.1em;
  font-weight: 600;
  color: var(--ion-color-dark);
}

.empty-submessage {
  margin: 0;
  font-size: 0.9em;
  color: var(--ion-color-medium-shade);
  line-height: 1.4;
}

/* Dark mode support */
@media (prefers-color-scheme: dark) {
  .course-content {
    background: var(--ion-color-step-50);
    border-color: var(--ion-color-step-200);
  }

  .course-title {
    color: var(--ion-color-light);
  }

  .course-description {
    color: var(--ion-color-step-600);
  }

  .assignments-content {
    background: var(--ion-color-step-50);
    border-color: var(--ion-color-step-200);
  }

  .section-title {
    color: var(--ion-color-light);
  }

  .empty-message {
    color: var(--ion-color-light);
  }
}

/* Responsive design */
@media (max-width: 768px) {
  .course-content {
    margin: 8px;
  }

  .assignments-section {
    margin: 8px;
  }

  .course-header {
    padding: 20px;
  }

  .course-title {
    font-size: 1.5em;
  }
}
</style>
