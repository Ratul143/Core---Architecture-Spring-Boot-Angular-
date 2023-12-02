import { FormGroup } from '@angular/forms';

export type Value = number | string | boolean;

export const markFormGroupTouched = (formGroup: FormGroup) => {
  (Object as any).values(formGroup.controls).forEach((control: any) => {
    control.markAsTouched();

    if (control.controls) {
      markFormGroupTouched(control);
    }
  });
};

export interface Icon {
  src: string;
  cssClass: string;
}

export interface ControlItem {
  value: Value;
  label: string;
  icon?: Icon;
}

export interface Control {
  items?: ControlItem[];
  changed?: () => void;
  map?: () => void;
}

export interface FormDetail {
  label: string;
  valueBn: string;
  valueEn: string;
  type: 'Normal' | 'Description';
}

// export interface ControlEntities {
//     [key: string]: Control;
// }

// export const mapControls = (controls: ControlEntities): void => {
//     Object.keys(controls).forEach(key => {
//         if (controls[key].map) {
//             controls[key].map();
//         }
//     });
// };
