import { NativeEventEmitter, NativeModules } from 'react-native';

type StepCounterType = {
  start(): () => void;
  stop(): () => void;
};

const { StepCounter } = NativeModules;

export const useStepListener = (onStep: (timestamp: number) => void) => {
  const eventEmitter = new NativeEventEmitter(StepCounter);
  const eventListener = eventEmitter.addListener(
    'StepRegistered',
    (timestamp) => onStep(Number(timestamp))
  );
  return () => {
    eventListener.remove();
  };
};

export default StepCounter as StepCounterType;
