<template>
    <div>
        <div v-if="isLoading" class="d-flex justify-center mt-5">
            <v-progress-circular size="50" color="primary" indeterminate />
        </div>

		<canvas ref="articlesChart" height="400" width="1200"></canvas>
    </div>
</template>

<script>
import axios from "axios";
import { Chart, registerables } from "chart.js";

Chart.register(...registerables);

const colors = ["red", "blue", "green"]

export default {
    name: "ChartsPage",
    data: () => ({
        yearArticles: [],
        isLoading: true
    }),
    mounted() {
        const subjectNames = this.$route.query.subjects.split(",");

        Promise.all([this.initYearArticleChart(subjectNames)]).then(() => (this.isLoading = false));
    },
    methods: {
        async initYearArticleChart(subjectNames) {
            const yearArticlesPromises = subjectNames.map(name => {
                return new Promise(resolve => {
                    axios
                        .get(
                            `https://frontier-analytics.kovalev.team/year-articles?query=${name}&start_year=2015&end_year=2020`
                        )
                        .then(d =>
                            resolve({
                                name,
                                data: d.data.data
                            })
                        );
                });
            });

            const yearArticles = await Promise.all(yearArticlesPromises);

            const labels = ["2015", "2016", "2017", "2018", "2019", "2020"];
            const datasets = yearArticles.map((a, i) => ({
                label: a.name,
                data: a.data.map(d => d.count),
                borderColor: colors[i] ?? "gray"
            }));

            const ctx = this.$refs.articlesChart;
            new Chart(ctx, {
                type: "line",
                data: {
                    labels,
                    datasets
                },
                options: {
                    responsive: false,
                    plugins: {
                        legend: {
                            position: "top"
                        },
                        title: {
                            display: true,
                            text: "Статьи"
                        }
                    }
                }
            });
        }
    }
};
</script>

<style scoped></style>
