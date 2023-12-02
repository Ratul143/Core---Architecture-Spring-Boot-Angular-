export interface IButtonDescription {
  color?:
    | 'primary'
    | 'accent'
    | 'warn'
    | 'secondary'
    | 'success'
    | 'warning'
    | 'danger'
    | 'info'; // Defaults to 'primary'
  text: string;
  toolTip?: string;
  icon?: string; // MAT-ICON !!NOT!! FAB-ICON
  listener: (data?: any) => any;
  disabled?: (data?: any) => boolean;
}
