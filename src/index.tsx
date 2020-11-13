import { NativeEventEmitter, NativeModules } from 'react-native';

type StepCounterType = {
  start(): () => void;
  stop(): () => void;
};

const { StepCounter } = NativeModules;

export const useStepListener = (onStep: (timestamp: string) => void) => {
  const eventEmitter = new NativeEventEmitter(StepCounter);
  const eventListener = eventEmitter.addListener(
    'StepRegistered',
    (timestamp) => onStep(timestamp)
  );
  return () => {
    eventListener.remove();
  };
};

export default StepCounter as StepCounterType;
