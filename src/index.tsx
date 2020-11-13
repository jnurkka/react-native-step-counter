import { NativeEventEmitter, NativeModules } from 'react-native';

type StepCounterType = {
  start(): () => void;
  stop(): () => void;
};

const { StepCounter } = NativeModules;

export const useAccelerometer = (onValue: (values: Array<number>) => void) => {
  const eventEmitter = new NativeEventEmitter(StepCounter);
  const eventListener = eventEmitter.addListener(
    'AccelerometerChange',
    (event) => onValue(event.split(','))
  );
  return () => {
    eventListener.remove();
  };
};

export default StepCounter as StepCounterType;
