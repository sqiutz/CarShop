//draw charts in pages
function drawChart(div, value) {
    if(value >= 0) {
        var data = [
                    {
                        'color'       : value < 1 ? 'rgb(244,84,134)' : '#C33C3C',
                        'value'       : value <= 1 ? value : 1
                    },
                    {
                        'color'       : 'rgb(54,54,54)',
                        'value'       : value <= 1 ? 1 - value : 0
                    }
        ];
        var label = parseInt(value * 100) + '%';
        drawPieChart(div, data, label);
    }
    
}

function drawPieChart(elementId, data, label) {
    var containerEl = document.getElementById(elementId),
        width = containerEl.clientWidth,
        height = containerEl.clientHeight,
        radius = Math.min(width, height) / 2,
        container = d3.select(containerEl),
        svg = container.append('svg')
                       .attr('width', width).attr('height', height);
    var pie = svg.append('g').attr('transform', 'translate(' + width / 2 + ',' + height / 2 + ')');
    

    var pieData = d3.layout.pie().sort(null)
                    .value(function(d) {return d.value;});

    var arc = d3.svg.arc()
                .outerRadius(radius - 5)
                .innerRadius((radius - 5) * 0.80);

    pie.datum(data)
        .selectAll('path')
        .data(pieData)
        .enter()
        .append('path')
        .attr('fill', function(d) {
            return d.data.color;
        })
        .attr("stroke", "#000000")
        .attr("stroke-width", 0.5)
        .attr('d', arc);
    
    svg.select('g').append('circle').attr('r', 30)
       .attr('fill', 'rgb(54,54,54)');
    svg.select('g').append('text').attr('text-anchor', 'middle')
       .attr('fill', data[0].value < 1 ? '#ffffff' : '#C33C3C').attr('y', 8)
       .text(label).attr('class', 'h3');
}