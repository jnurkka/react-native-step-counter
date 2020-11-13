import React from 'react';
import { StyleSheet, View, Text } from 'react-native';
import StepCounter, { useStepListener } from 'react-native-step-counter';

StepCounter.start();

export default function App() {
  const [result, setResult] = React.useState<string | undefined>();

  useStepListener((timestamp: string) => {
    const stepDate = new Date(parseInt(timestamp, 10)).toISOString();
    setResult(stepDate);
  });

  return (
    <View style={styles.container}>
      <Text>Last step at: {result}</Text>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
  },
});
