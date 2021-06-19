<template>
    <div class="subject-card" :class="{ selected }" @click="$emit('toggleSelect')">
        <div>
            <div class="name">{{ subject.name }}</div>
            <div class="branch_name">{{ subject.branch_name.branchScience.name }}</div>
        </div>
        <div class="chart">
            <canvas ref="canvas" width="100" height="100"></canvas>
        </div>

        <div class="img_content">
        <div class="icon align-stretch">
			<!--TODO: v-if just for test-->
			<img v-if="subject.isFrontir == '1'" src="../assets/img/fire.svg" alt="Subject is frontier">
			<img v-else src="../assets/img/white.svg" alt="Subject in promising direction">
        </div>
		</div>
    </div>
</template>

<script>
import { Chart, registerables } from "chart.js";

Chart.register(...registerables);

export default {
    name: "SubjectCard",
    props: {
        subject: {
            type: Object
        },
        selected: {
            type: Boolean,
            required: true
        }
    },
    mounted() {
        const data = {
            labels: ["Day 1", "Day 2", "Day 3", "Day 4", "Day 5", "Day 6"],
            datasets: [
                {
                    label: "Dataset",
                    data: [10, 20, -1, 10, 23, 12],
                    borderColor: "red",
                    fill: false
                }
            ]
        };
        const ctx = this.$refs.canvas;
        new Chart(ctx, {
            type: "line",
            data: data,
            options: {
                plugins: {
                    legend: { display: false },
                    tooltip: { enabled: false }
                },
                animation: false,
                scales: {
                    x: { display: false },
                    y: { display: false }
                }
            }
        });
    }
};
</script>

<style scoped lang="scss">
.subject-card {
    border: 1px solid #d1d5db;
    box-shadow: 0 1px 2px rgba(0, 0, 0, 0.05);
    border-radius: 8px;
    padding: 15px 40px;
    display: flex;
    align-items: center;
    justify-content: space-between;
    cursor: pointer;
    user-select: none;

    position: relative;

    &.selected {
        background-color: #d1fae5;
        border-color: #10b981;
        color: #047857;
    }
}
.name {
    min-width: 400px;
    font-size: 20px;
    font-weight: normal;
}
.icon {
    position: absolute;
    top:10px;
    right: 10px;
}
.branch_name {
    opacity: 0.5;
    padding-top: 6px;
}
</style>
