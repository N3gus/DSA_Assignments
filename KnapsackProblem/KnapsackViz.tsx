import React, { useState, useEffect } from 'react';
import { Play, Pause, SkipBack, SkipForward } from 'lucide-react';
import { Card, CardHeader, CardTitle, CardContent } from '@/components/ui/card';

// Move these outside the component
const items = [
  { name: "A", weight: 5, value: 10, ratio: 2.00 },
  { name: "B", weight: 3, value: 8, ratio: 2.67 },
  { name: "C", weight: 2, value: 5, ratio: 2.50 },
  { name: "D", weight: 4, value: 7, ratio: 1.75 },
  { name: "E", weight: 1, value: 3, ratio: 3.00 },
  { name: "F", weight: 6, value: 12, ratio: 2.00 }
];

const sortedItems = [...items].sort((a, b) => b.ratio - a.ratio);
const capacity = 15;

const KnapsackVisualizer = () => {
  const [currentStep, setCurrentStep] = useState(0);
  const [isPlaying, setIsPlaying] = useState(false);
  const [speed, setSpeed] = useState(2000);

  // Generate steps for the animation
  const steps = [
    { 
      phase: 'initial',
      title: 'Initial Items',
      description: 'Starting state showing all items with their properties',
      items: items,
      selected: [],
      totalWeight: 0,
      totalValue: 0
    },
    { 
      phase: 'sorting',
      title: 'Sorting by Value/Weight Ratio',
      description: 'Items are sorted by their value-to-weight ratio in descending order',
      items: sortedItems,
      selected: [],
      totalWeight: 0,
      totalValue: 0
    }
  ];

  // Add selection steps
  sortedItems.forEach((item, index) => {
    const prevStep = steps[steps.length - 1];
    const prevSelected = prevStep.selected;
    const canAdd = (prevSelected.reduce((sum, i) => sum + i.weight, 0) + item.weight) <= capacity;
    
    steps.push({
      phase: 'selection',
      title: `Processing Item ${item.name}`,
      description: canAdd 
        ? `Adding item ${item.name} (fits within remaining capacity)`
        : `Skipping item ${item.name} (would exceed capacity)`,
      items: sortedItems,
      selected: canAdd ? [...prevSelected, item] : prevSelected,
      currentItem: item,
      totalWeight: canAdd 
        ? prevSelected.reduce((sum, i) => sum + i.weight, 0) + item.weight
        : prevSelected.reduce((sum, i) => sum + i.weight, 0),
      totalValue: canAdd
        ? prevSelected.reduce((sum, i) => sum + i.value, 0) + item.value
        : prevSelected.reduce((sum, i) => sum + i.value, 0)
    });
  });

  // Auto-play effect
  useEffect(() => {
    let interval;
    if (isPlaying && currentStep < steps.length - 1) {
      interval = setInterval(() => {
        setCurrentStep(prev => prev + 1);
      }, speed);
    }
    return () => clearInterval(interval);
  }, [isPlaying, currentStep, steps.length, speed]);

  // Stop playing when we reach the end
  useEffect(() => {
    if (currentStep === steps.length - 1) {
      setIsPlaying(false);
    }
  }, [currentStep, steps.length]);

  const step = steps[currentStep];

  const getItemColor = (item) => {
    if (step.phase === 'selection' && item.name === step.currentItem.name) {
      return 'bg-yellow-100';
    }
    if (step.selected.find(i => i.name === item.name)) {
      return 'bg-green-100';
    }
    if (step.phase === 'selection' && 
        !step.selected.find(i => i.name === item.name) && 
        sortedItems.findIndex(i => i.name === item.name) < 
        sortedItems.findIndex(i => i.name === step.currentItem.name)) {
      return 'bg-gray-100';
    }
    return 'bg-white';
  };

  return (
    <Card className="w-full max-w-4xl">
      <CardHeader>
        <CardTitle>Greedy Knapsack Algorithm Visualization</CardTitle>
      </CardHeader>
      <CardContent>
        <div className="space-y-6">
          {/* Controls */}
          <div className="flex items-center justify-center space-x-4">
            <button 
              className="p-2 hover:bg-gray-100 rounded"
              onClick={() => setCurrentStep(0)}
              disabled={currentStep === 0}
            >
              <SkipBack className={currentStep === 0 ? "text-gray-400" : "text-gray-700"} />
            </button>
            <button 
              className="p-2 hover:bg-gray-100 rounded"
              onClick={() => setIsPlaying(!isPlaying)}
            >
              {isPlaying ? <Pause className="text-gray-700" /> : <Play className="text-gray-700" />}
            </button>
            <button 
              className="p-2 hover:bg-gray-100 rounded"
              onClick={() => setCurrentStep(Math.min(currentStep + 1, steps.length - 1))}
              disabled={currentStep === steps.length - 1}
            >
              <SkipForward className={currentStep === steps.length - 1 ? "text-gray-400" : "text-gray-700"} />
            </button>
            <select 
              value={speed}
              onChange={(e) => setSpeed(Number(e.target.value))}
              className="ml-4 p-1 border rounded"
            >
              <option value={3000}>Slow</option>
              <option value={2000}>Normal</option>
              <option value={1000}>Fast</option>
            </select>
          </div>

          {/* Status */}
          <div className="text-center">
            <h3 className="text-lg font-semibold">{step.title}</h3>
            <p className="text-gray-600">{step.description}</p>
          </div>

          {/* Progress bar */}
          <div className="relative pt-1">
            <div className="flex mb-2 items-center justify-between">
              <div>
                <span className="text-xs font-semibold inline-block py-1 px-2 uppercase rounded-full text-blue-600 bg-blue-200">
                  Progress
                </span>
              </div>
              <div className="text-right">
                <span className="text-xs font-semibold inline-block text-blue-600">
                  {Math.round((currentStep / (steps.length - 1)) * 100)}%
                </span>
              </div>
            </div>
            <div className="overflow-hidden h-2 mb-4 text-xs flex rounded bg-blue-200">
              <div 
                className="shadow-none flex flex-col text-center whitespace-nowrap text-white justify-center bg-blue-500 transition-all duration-500"
                style={{ width: `${(currentStep / (steps.length - 1)) * 100}%` }}
              />
            </div>
          </div>

          {/* Main visualization */}
          <div className="grid grid-cols-2 gap-6">
            {/* Items table */}
            <div className="border rounded-lg p-4">
              <h3 className="font-semibold mb-2">Items</h3>
              <div className="overflow-hidden">
                <table className="min-w-full">
                  <thead>
                    <tr>
                      <th className="px-4 py-2 text-left">Item</th>
                      <th className="px-4 py-2 text-right">Weight</th>
                      <th className="px-4 py-2 text-right">Value</th>
                      <th className="px-4 py-2 text-right">Ratio</th>
                    </tr>
                  </thead>
                  <tbody>
                    {step.items.map((item) => (
                      <tr 
                        key={item.name}
                        className={`transition-colors duration-300 ${getItemColor(item)}`}
                      >
                        <td className="px-4 py-2">{item.name}</td>
                        <td className="px-4 py-2 text-right">{item.weight}</td>
                        <td className="px-4 py-2 text-right">{item.value}</td>
                        <td className="px-4 py-2 text-right">{item.ratio.toFixed(2)}</td>
                      </tr>
                    ))}
                  </tbody>
                </table>
              </div>
            </div>

            {/* Knapsack status */}
            <div className="border rounded-lg p-4">
              <h3 className="font-semibold mb-2">Knapsack Status</h3>
              <div className="space-y-4">
                <div>
                  <p className="text-sm text-gray-600">Capacity: {capacity}</p>
                  <div className="w-full bg-gray-200 rounded-full h-2.5">
                    <div 
                      className="bg-blue-600 h-2.5 rounded-full transition-all duration-500"
                      style={{ width: `${(step.totalWeight / capacity) * 100}%` }}
                    />
                  </div>
                  <p className="text-sm text-gray-600 mt-1">
                    Weight: {step.totalWeight} / {capacity}
                  </p>
                </div>

                <div>
                  <p className="text-sm font-medium">Selected Items:</p>
                  <ul className="list-none pl-0">
                    {step.selected.map((item) => (
                      <li key={item.name} className="text-sm">
                        Item {item.name} (Value: {item.value}, Weight: {item.weight})
                      </li>
                    ))}
                  </ul>
                </div>

                <div>
                  <p className="text-sm font-medium">Total Value: {step.totalValue}</p>
                </div>
              </div>
            </div>
          </div>
        </div>
      </CardContent>
    </Card>
  );
};

export default KnapsackVisualizer;
