import { Icon } from '../utility/utils/form';

export type Value = number | string | boolean;

export interface ControlItem {
  value: Value;
  label: string;
  labelBn: string;
  icon?: Icon;
}
