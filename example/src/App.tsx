import React from 'react';
import { StyleSheet, View, Text } from 'react-native';
import StepCounter, { useAccelerometer } from 'react-native-step-counter';

export default function App() {
  const [result, setResult] = React.useState<number | undefined>();

  const removeListener = useAccelerometer((values) => {
    setResult(values[0]);
  });

  React.useEffect(() => {
    StepCounter.start();
    return () => {
      removeListener();
      StepCounter.stop();
    };
  }, [removeListener]);

  return (
    <View style={styles.container}>
      <Text>Result: {result}</Text>
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
