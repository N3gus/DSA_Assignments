# Knapsack Problem: Greedy Algorithm Analysis
## Technical Report

### 1. Algorithm Design

#### Chosen Strategy
The selected greedy strategy sorts items by their value-to-weight ratio (V/W) in descending order. This approach prioritizes items that provide the most value per unit of weight, attempting to maximize the overall value while respecting the weight constraint.

#### Rationale
- **Efficiency**: The strategy makes locally optimal choices at each step, hoping to achieve a globally optimal or near-optimal solution.
- **Simplicity**: The approach is straightforward to implement and understand.
- **Speed**: Compared to exhaustive search or dynamic programming, this method provides a solution in O(n log n) time.

#### Alternative Approaches Considered
1. **Sort by Value**: Selecting items with highest absolute value first
   - Rejected because it might choose heavy items that consume too much capacity
2. **Sort by Weight**: Selecting lightest items first
   - Rejected because it might select low-value items
3. **Dynamic Programming**: Optimal but more complex solution
   - Rejected for this implementation due to higher complexity and resource requirements

### 2. Implementation

#### Step-by-Step Solution

**Initial Setup:**
1. Sort items by V/W ratio in descending order:
```
E (3.0) > B (2.67) > C (2.5) > A (2.0) = F (2.0) > D (1.75)
```

**Decision Process Table:**

| Step | Item | V/W Ratio | Weight(Kg) | Value(K) | Running Weight | Running Value | Decision | Reason |
|------|------|-----------|---------|--------|----------------|---------------|-----------|---------|
| 1 | E | 3.0 | 1 | 3 | 1 | 3 | Accept | Fits, highest ratio |
| 2 | B | 2.67 | 3 | 8 | 4 | 11 | Accept | Fits, second highest |
| 3 | C | 2.5 | 2 | 5 | 6 | 16 | Accept | Fits, third highest |
| 4 | A | 2.0 | 5 | 10 | 11 | 26 | Accept | Fits within limit |
| 5 | F | 2.0 | 6 | 12 | 17 | - | Reject | Exceeds weight limit |
| 6 | D | 1.75 | 4 | 7 | 15 | - | Reject | Exceeds weight limit |

### 3. Results Analysis

#### Final Selection
- Selected Items: E, B, C, A
- Total Weight: 11 kg
- Total Value: K26

#### Constraint Verification
- Weight Limit: 11 kg < 15 kg (Satisfied)
- Item Uniqueness: Each item used once (Satisfied)
- Item Integrity: No items divided (Satisfied)

### 4. Algorithm Analysis

#### Time Complexity: O(n log n)
- Sorting step: O(n log n)
- Selection step: O(n)
- Overall complexity dominated by sorting: O(n log n)

#### Space Complexity: O(n)
- Storage for sorted array: O(n)
- Additional variables: O(1)
- Total space: O(n)

#### Limitations and Edge Cases

1. **Optimality**
   - Not guaranteed to find the global optimal solution
   - Can miss better solutions due to local decision making

2. **Edge Cases**
   - Very similar V/W ratios might lead to suboptimal choices
   - Large weight capacity differences between items can affect solution quality
   - Items with correlation between weight and value might challenge the ratio-based approach

3. **Constraints Impact**
   - The 0/1 constraint (no fractional items) can lead to suboptimal solutions
   - Weight capacity constraints might force rejection of high-ratio items later

### 5. Comparison with Dynamic Programming

#### Greedy vs Dynamic Programming Approach

**Advantages of Greedy**:
- Faster execution: O(n log n) vs O(nW) for DP
- Simpler implementation
- Lower memory requirements
- Good for real-time applications

**Advantages of Dynamic Programming**:
- Guarantees optimal solution
- Handles edge cases better
- More flexible for constraint modifications
- Better for smaller input sizes where optimality is crucial

#### Failure Scenarios for Greedy Approach

Consider this counter-example:
```
Capacity: 10 kg
Items: 
X: 6 kg, K8 (ratio 1.33)
Y: 5 kg, K7 (ratio 1.40)
Z: 4 kg, K5 (ratio 1.25)
```

The greedy algorithm would choose Y first, then be unable to fit X, resulting in total value 7K. However, the optimal solution would be X + Z = 13K.

### 6. Conclusions

The implemented greedy algorithm provides a good balance between:
- Implementation simplicity
- Execution efficiency
- Solution quality

While not guaranteed to find the optimal solution, it performs well for many practical scenarios and can be implemented efficiently. For applications requiring absolute optimality, dynamic programming would be recommended instead.